
# Use a JDK base image
FROM openjdk:17-jdk

# Set working directory
WORKDIR /app

# Copy jar (after mvn package or gradle build)
COPY target/PaymentService-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
# Correct ENTRYPOINT
ENTRYPOINT ["java", "-jar", "app.jar"]