# 빌드 스테이지
FROM gradle:8.8-jdk17-alpine AS build
WORKDIR /home/gradle/src
COPY --chown=gradle:gradle . .
RUN gradle build --no-daemon

# 실행 스테이지
FROM openjdk:17-jdk-slim
WORKDIR /root
COPY --from=build /home/gradle/src/jup-jup-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]