FROM mcr.microsoft.com/dotnet/aspnet:8.0 AS base
USER $APP_UID
WORKDIR /app
EXPOSE 8080
EXPOSE 8081

FROM mcr.microsoft.com/dotnet/sdk:8.0 AS build
ARG BUILD_CONFIGURATION=Release
WORKDIR /src
COPY ["stackoverflow_statistics/stackoverflow_statistics.csproj", "stackoverflow_statistics/"]
RUN dotnet restore "stackoverflow_statistics/stackoverflow_statistics.csproj"
COPY . .
WORKDIR "/src/stackoverflow_statistics"
RUN dotnet build "stackoverflow_statistics.csproj" -c $BUILD_CONFIGURATION -o /app/build

FROM build AS publish
ARG BUILD_CONFIGURATION=Release
RUN dotnet publish "stackoverflow_statistics.csproj" -c $BUILD_CONFIGURATION -o /app/publish /p:UseAppHost=false

FROM base AS final
WORKDIR /app
COPY --from=publish /app/publish .
ENTRYPOINT ["dotnet", "stackoverflow_statistics.dll"]
