FROM openjdk:8-jdk-alpine

VOLUME /tmp

EXPOSE 8080

ARG JAR_FILE=build/libs/Mattermost-Alert-Manager-v0.0.1.jar

ADD ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]