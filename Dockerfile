FROM maven:latest as build
COPY . /usr/src/
WORKDIR /usr/src/
RUN mvn clean package
RUN ls .

FROM openjdk:17.0.2
RUN mkdir -p /files
VOLUME /files
WORKDIR /app

ARG JAR_FILE=target/*.jar
COPY --from=build /usr/src/${JAR_FILE} /app/app.jar
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=dev", "/app/app.jar"]
