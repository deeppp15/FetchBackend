FROM openjdk:17-alpine

WORKDIR /app
ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]