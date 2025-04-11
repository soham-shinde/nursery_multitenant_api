# Build stage: compile the project using Maven with Java 21
FROM maven:3.9.4-eclipse-temurin-21 AS build
WORKDIR /app
# Copy only the pom.xml first to leverage Docker caching
COPY pom.xml .
# Copy the source code
COPY src ./src
# Build the project and skip tests for faster build times
RUN mvn clean package -DskipTests

# Runtime stage: use a lightweight OpenJDK 21 image
FROM openjdk:21-slim
WORKDIR /app
# Copy the generated jar from the build stage; adjust the jar name if needed.
COPY --from=build /app/target/api-0.0.1-SNAPSHOT.jar app.jar
# Expose the port that the Spring Boot app listens on
EXPOSE 8080
# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
