FROM maven:3.9.7-amazoncorretto-17 as build
WORKDIR /app
COPY . .
RUN mvn clean package -X -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build ./app/target/*.jar ./techmesa.jar
ENTRYPOINT java -jar techmesa.jar