# ---------- Stage 1: Build ----------
FROM gradle:8.5-jdk17 AS build
WORKDIR /app

# Copy everything first
COPY . .

# Ensure gradlew is Linux-compatible & executable
RUN sed -i 's/\r$//' gradlew && chmod +x gradlew

# Optional: pull dependencies early (won't fail build)
RUN ./gradlew dependencies --no-daemon || true

# Build the JAR
RUN ./gradlew clean build -x test --no-daemon


# ---------- Stage 2: Runtime ----------
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app

# Copy built JAR
COPY --from=build /app/build/libs/*.jar /app/app.jar

# Expose Spring Boot port
EXPOSE 8080

# Start the application
ENTRYPOINT ["java", "-jar", "app.jar"]