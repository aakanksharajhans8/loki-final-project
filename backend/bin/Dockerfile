# Stage 1: Build the application using Maven
# Stage 1: The "Build" Environment
# This stage uses a base image that has Maven and Java 21 to build our project.
FROM maven:3.9.6-eclipse-temurin-21 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml first. Docker caches layers, so if the pom.xml hasn't changed,
# it won't re-download dependencies, making builds faster.
COPY pom.xml .

# Copy the rest of your application's source code
COPY src ./src

# Run the Maven package command to compile the code and create a .jar file.
# We skip tests because we want a fast build for the image.
RUN mvn -DskipTests clean package

# Stage 2: The "Run" Environment
# This stage uses a much smaller, more secure base image that only contains the Java Runtime.
FROM eclipse-temurin:21-jre-jammy

# Set the working directory
WORKDIR /app

# Copy the compiled .jar file from the 'build' stage into this final image.
# The wildcard (*) handles any version changes in the snapshot.
COPY --from=build /app/target/quotation-service-*.jar ./quotation-service.jar

# Tell Docker which port the application will run on.
EXPOSE 8083

# The command that will be executed when the container starts.
ENTRYPOINT ["java", "-jar", "quotation-service.jar"]



