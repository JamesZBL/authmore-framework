FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 8083
ARG JAR_FILE=build/libs/authmore-admin-0.0.1-SNAPSHOT.jar
ARG JAR_DEST=/app/app.jar
WORKDIR /app
COPY ${JAR_FILE} ${JAR_DEST}
ENTRYPOINT ["java", "-Dspring.profiles.active=test", "-jar", "app.jar", "-Xmx32m", "-Xss256k"]