FROM openjdk:21-jdk
WORKDIR /app
COPY ./target/dev-pro-0.0.1-SNAPSHOT.jar /app/app.jar
CMD [ "java", "-jar", "/app/app.jar"]

