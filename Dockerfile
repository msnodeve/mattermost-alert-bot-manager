FROM openjdk:8-jdk-alpine

RUN ln -snf /usr/share/zoneinfo/Asia/Seoul /etc/localtime
RUN mkdir images

VOLUME ["/tmp", "/images"]

EXPOSE 8080

ARG JAR_FILE=build/libs/Mattermost-Alert-Manager-v0.0.1.jar

ADD ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]