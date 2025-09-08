# Stage 1: Build the application
FROM maven:3.8.5-openjdk-21 AS build

# Set the working directory
WORKDIR /app

# Copy the pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the rest of the application source code
COPY src ./src

# Package the application
RUN mvn package -DskipTests

# Stage 2: Create the final, lean image
FROM openjdk:21-slim

# Set the working directory
WORKDIR /app

# Copy the executable JAR from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port the application runs on
EXPOSE 8080

# Set the entry point for the container
ENTRYPOINT ["java", "-jar", "app.jar"]
