FROM openjdk:11-jdk

WORKDIR /app

COPY .mvn .
COPY src .
COPY pom.xml .
COPY mvnw .

RUN ./mvnw dependency:go-offline

CMD ["./mvnw", "spring-boot:run"]