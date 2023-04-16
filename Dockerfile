FROM gradle:latest AS build

RUN apt-get install openjdk-17-jdk -y

RUN ./gradlew bootJar

FROM openjdk:17-jdk-slim

WORKDIR /Quote-App

COPY build/libs/Quote_App-1.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
