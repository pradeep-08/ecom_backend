# ---------- Stage 1: Build ----------
FROM gradle:8.5-jdk17 AS build
WORKDIR /app

# Copy everything first (simple + prevents losing exec bit later)
COPY . .

# Ensure gradlew is unix-y and executable (covers Windows checkouts)
RUN sed -i 's/\r$//' gradlew && chmod +x gradlew

# (Optional) warm up dependencies; don't fail if nothing to resolve
RUN ./gradlew dependencies --no-daemon || true

# Build the JAR (skip tests for speed)
RUN ./gradlew clean build -x test --no-daemon

# ---------- Stage 2: Runtime ----------
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy the built artifact(s) from the build stage
COPY --from=build /app/build/libs/*.jar /app/app.jar

# Expose Spring Boot default port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
