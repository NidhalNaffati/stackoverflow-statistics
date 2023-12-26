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