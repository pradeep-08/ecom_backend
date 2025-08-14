# Stage 1: Build the application
FROM gradle:8.5-jdk17 AS build
WORKDIR /app

# Copy Gradle build files first (better caching)
COPY build.gradle settings.gradle gradlew ./
COPY gradle gradle

# Download dependencies (for caching)
RUN ./gradlew dependencies || return 0

# Copy the rest of the project
COPY . .

# Build the JAR (skip tests for speed)
RUN ./gradlew clean build -x test

# Stage 2: Create the runtime image
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy the built JAR from the build stage
# This picks the first JAR in build/libs without hardcoding the name
COPY --from=build /app/build/libs/*.jar app.jar

# Expose Spring Boot port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
