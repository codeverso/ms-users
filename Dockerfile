FROM maven:latest as build
COPY . /usr/src/
WORKDIR /usr/src/
RUN mvn clean package

FROM openjdk:17.0.2
EXPOSE 8080

ARG JAR_FILE=target/*.jar
COPY --from=build /usr/src/${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=dev", "app.jar"]
