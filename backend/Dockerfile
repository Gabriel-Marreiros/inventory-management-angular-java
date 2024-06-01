# FROM eclipse-temurin:17-jdk-focal
FROM openjdk:17-jdk-alpine

EXPOSE 8080
 
WORKDIR /app
 
COPY src ./src

COPY .mvn/ .mvn

COPY mvnw pom.xml ./

RUN chmod +x mvnw

RUN ./mvnw dependency:go-offline
 
CMD ["./mvnw", "spring-boot:run", "-Dspring-boot.run.profiles=prod"]