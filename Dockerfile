FROM openjdk:17.0.2
ARG JAR_FILE=target/*.jar
ADD ${JAR_FILE} /app/app.jar
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=dev", "/app/app.jar"]
EXPOSE 8080