FROM openjdk:17.0.1-jdk-oracle

RUN mkdir /app
COPY target/scrapper-1.0-SNAPSHOT.jar /app/scrapper-1.0-SNAPSHOT.jar
COPY src/main/resources/application.properties /app/application.properties
WORKDIR /app

CMD ["java", "-jar", "scrapper-1.0-SNAPSHOT.jar"]