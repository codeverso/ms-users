FROM amazoncorretto:17
VOLUME /tmp
ARG JAR_FILE
COPY /target/ms-users-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT exec java -jar /app.jar -Djava.security.egd=file:/dev/./urandom $JAVA_OPTS