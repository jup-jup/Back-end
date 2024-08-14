FROM openjdk:17
LABEL authors="main"
ARG JAR_FILE=build/libs/jenkins-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} docker-springboot.jar
ENTRYPOINT ["java", "-jar", "/docker-springboot.jar", ">", "app.log"]