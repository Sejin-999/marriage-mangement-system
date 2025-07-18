# Java 기반 스프링 부트 앱 실행용 Dockerfile
FROM eclipse-temurin:17-jdk-alpine

VOLUME /tmp

ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app.jar"]
