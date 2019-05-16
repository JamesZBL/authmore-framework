FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE=build/libs/authmore-platform-0.0.1-SNAPSHOT.jar
ARG JAR_DEST=/app/app.jar
WORKDIR /app
COPY ${JAR_FILE} ${JAR_DEST}
ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "app.jar"]