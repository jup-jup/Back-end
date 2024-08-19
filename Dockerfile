# 베이스 이미지로 Java 17을 사용
FROM openjdk:17-jdk-slim
# 작업 디렉토리 설정
WORKDIR /app
# JAR 파일 복사
COPY /Users/boramkim/Desktop/project files/jup-jup/build/libs/jup-jup-0.0.1-SNAPSHOT.jar app.jar/
# 포트 설정 (Spring Boot 기본 포트)
EXPOSE 8080
# 애플리케이션 실행 명령
ENTRYPOINT ["java", "-jar", "app.jar"]