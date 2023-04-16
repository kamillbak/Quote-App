FROM gradle:latest AS build

RUN apt-get update
RUN apt-get install openjdk-17-jdk -y
COPY . .

RUN ./gradlew bootJar --no-daemon

FROM openjdk:17-jdk-slim

COPY build/libs/Quote_App-1.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
