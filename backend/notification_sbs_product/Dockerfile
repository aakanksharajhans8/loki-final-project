# syntax=docker/dockerfile:1
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
RUN --mount=type=cache,target=/root/.m2 mvn -q -DskipTests dependency:go-offline
COPY src ./src
RUN --mount=type=cache,target=/root/.m2 mvn -q -DskipTests package
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
ENV JAVA_OPTS=""
ENV DB_URL="jdbc:mysql://mysql:3306/cams_notifications?createDatabaseIfNotExist=true&allowPublicKeyRetrieval=true&useSSL=false"
ENV DB_USERNAME="root"
ENV DB_PASSWORD="messiIsGoat,"
ENV MAIL_USERNAME="bhargavk0301@gmail.com"
ENV MAIL_PASSWORD="yrew ahsu qmnu ysgd"
ENV MAIL_FROM="bhargavk0301@gmail.com"
EXPOSE 8091
COPY --from=build /app/target/*.jar /app/app.jar
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar /app/app.jar"]
