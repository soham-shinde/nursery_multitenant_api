# Use a minimal OpenJDK 21 base image
FROM eclipse-temurin:21-jdk-alpine

# Set working directory
WORKDIR /app

# Copy built jar into image (use target directory where Maven outputs the jar)
COPY target/nursery_system-0.0.1-SNAPSHOT.jar app.jar

# Expose port used by Spring Boot
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
