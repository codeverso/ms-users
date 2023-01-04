FROM openjdk:17.0.2
WORKDIR /workspace/app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN --mount=type=cache,target=/root/.m2 ./mvnw install -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

FROM openjdk:17.0.2
VOLUME /tmp
ARG JAR_FILE=target/ms-users-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} /app/app.jar
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=dev", "/app/app.jar"]