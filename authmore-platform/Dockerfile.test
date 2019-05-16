FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 8086
ARG JAR_FILE=build/libs/authmore-platform-0.0.1-SNAPSHOT.jar
ARG JAR_DEST=/app/app.jar
WORKDIR /app
COPY ${JAR_FILE} ${JAR_DEST}
ENTRYPOINT ["java","-Xmx32m", "-Xss256k", "-Dspring.profiles.active=test", "-jar", "app.jar"]