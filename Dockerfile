# 빌드 스테이지
FROM gradle:7.2.0-jdk17 AS build
WORKDIR /home/gradle/src
COPY --chown=gradle:gradle . .
RUN gradle build --no-daemon

# 실행 스테이지
FROM eclipse-temurin:17-jre-alpine
COPY --from=build /home/gradle/src/build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]