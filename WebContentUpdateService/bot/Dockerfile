FROM openjdk:17.0.1-jdk-oracle

RUN mkdir /app
COPY target/bot-1.0-SNAPSHOT.jar /app/bot-1.0-SNAPSHOT.jar
COPY src/main/resources/application.properties /app/application.properties
WORKDIR /app

CMD ["java", "-jar", "bot-1.0-SNAPSHOT.jar"]