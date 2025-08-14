# Stage 1: Build the application
FROM gradle:8.5-jdk17 AS build
WORKDIR /app

# Copy wrapper + build files and make wrapper executable (best for caching)
COPY --chmod=755 gradlew ./ 
COPY gradle gradle
COPY build.gradle settings.gradle ./

# Warm up Gradle deps (don't fail the build if nothing to resolve)
RUN ./gradlew dependencies --no-daemon || true

# Copy the rest of the project
COPY . .

# Build the JAR (skip tests for speed)
RUN ./gradlew clean build -x test --no-daemon

# Stage 2: Create the runtime image
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy the built JAR from the build stage
COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
