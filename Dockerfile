FROM openjdk:21

RUN apt-get update && apt-get install -y maven

WORKDIR /app

COPY . /app

RUN mvn clean install -DskipTests

EXPOSE 8080

RUN cp target/api-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]