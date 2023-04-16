# FROM gradle:latest AS build
# RUN apt-get install openjdk-17-jdk -y
# RUN ./gradlew bootJar
# FROM openjdk:17-jdk-slim
# WORKDIR /Quote-App
# COPY build/libs/Quote_App-1.jar app.jar
# EXPOSE 8080
# ENTRYPOINT ["java", "-jar", "app.jar"]

FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
COPY build/libs/Quote_App-1.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8080