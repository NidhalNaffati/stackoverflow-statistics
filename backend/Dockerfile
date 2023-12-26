##### First stage: build the application

# Use an official maven image as a parent image
FROM maven:3.9.4-amazoncorretto-21 AS build

# Copy all the files from the current directory to the container
COPY . /app

# Set the working directory
WORKDIR /app

# Build the application and skip the tests
RUN mvn package -DskipTests


##### Second stage: build the runtime image

# Set the base image to use to java 21
FROM amazoncorretto:21-alpine

# Copy the jar to the container
COPY --from=build /app/target/stackoverflow-statistics*.jar /stackoverflow-statistics.jar

# Run the jar file
ENTRYPOINT ["java", "-jar", "/stackoverflow-statistics.jar"]

## this commented code is for docker-compose.yml allowing to run the application with postgres and redis
#services:
#  # PostgreSQL Database
#  postgres_db:
#    image: postgres:latest
#    container_name: postgres_db
#    environment:
#      POSTGRES_DB: postgres
#      POSTGRES_USER: postgres
#      POSTGRES_PASSWORD: postgres
#    ports:
#      - "5432:5432"
#    volumes:
#      - postgres_data:/var/lib/postgresql/data
#    networks:
#      - postgres
#    restart: unless-stopped
#
#  # Redis Cache
#  redis_cache:
#    image: redis:latest
#    container_name: redis_cache
#    ports:
#      - "6379:6379"
#    volumes:
#      - redis_data:/data
#    networks:
#      - redis
#    restart: unless-stopped
#
#  # Spring Boot Application
#  spring_app:
#    image: stackoverflow-statistics  # Use the image name you built in the previous steps
#    container_name: spring_app
#    environment:
#      POSTGRES_HOST: postgres_db  # Update to use the service name
#      POSTGRES_PORT: 5432
#      POSTGRES_DB: postgres
#      POSTGRES_USER: postgres
#      POSTGRES_PASSWORD: postgres
#      REDIS_HOST: redis_cache  # Update to use the service name
#      REDIS_PORT: 6379
#      # Add other environment variables for your Spring Boot app as needed
#    ports:
#      - "8080:8080"  # Adjust the port mapping as needed
#    networks:
#      - postgres
#      - redis
#    depends_on:
#      - postgres_db
#      - redis_cache
#    restart: unless-stopped
#
#networks:
#  postgres:
#  redis:
#
#volumes:
#  postgres_data:
#  redis_data:
