FROM openjdk:8-jdk-alpine
WORKDIR /app
COPY libs libs/
COPY resources resources/
COPY classes classes/
ENTRYPOINT ["java", "-Dspring.profiles.active=test", "-cp", "/app/resources:/app/classes:/app/libs/*", "me.zbl.authmore.auth.AdminApplication"]
EXPOSE 8083
