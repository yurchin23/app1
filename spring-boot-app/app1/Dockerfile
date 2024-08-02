FROM openjdk:11-jre-slim

WORKDIR /app

COPY target/myapp1-1.0.0.jar /app/myapp1.jar

ENTRYPOINT ["java", "-jar", "myapp1.jar"]

EXPOSE 8080
