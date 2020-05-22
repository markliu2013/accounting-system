FROM openjdk:8-jdk-alpine
ARG JAR_FILE=build/libs/accounting-system-server-0.1.0.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8080