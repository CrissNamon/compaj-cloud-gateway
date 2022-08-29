FROM openjdk:11-jdk

WORKDIR /app

COPY .mvn/ ./gateway-service/.mvn
COPY /gateway-service/src ./gateway-service/src
COPY /gateway-service/pom.xml ./gateway-service
COPY mvnw ./gateway-service
COPY pom.xml ./

WORKDIR /app/gateway-service

#RUN ./mvnw dependency:go-offline


CMD ["./mvnw", "spring-boot:run"]