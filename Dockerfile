# 빌드 스테이지
FROM gradle:8.8-jdk17 AS build
# 작업 디렉토리 설정
WORKDIR /app
# 의존성 캐싱을 위해 build.gradle과 settings.gradle만 먼저 복사
COPY build.gradle settings.gradle ./
# 의존성 다운로드 (이 단계는 소스 코드가 변경되어도 캐시됨)
RUN gradle dependencies --no-daemon
# 나머지 소스 코드 복사
COPY src ./src
# 애플리케이션 빌드
RUN gradle build --no-daemon

# 실행 스테이지
FROM --platform=linux/amd64 eclipse-temurin:17-jre-alpine
# 작업 디렉토리 설정
WORKDIR /app
# 비루트 사용자 및 그룹 생성
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
# logs 디렉토리 생성 및 권한 설정
RUN mkdir -p /app/logs && chown -R appuser:appgroup /app/logs
# 빌드 스테이지에서 생성된 JAR 파일만 복사
COPY --from=build /app/build/libs/*.jar app.jar
# dumb-init 설치
RUN apk add --no-cache dumb-init
# 비루트 사용자로 실행
USER appuser
# 컨테이너 실행 명령
ENTRYPOINT ["/usr/bin/dumb-init", "java", "-jar", "app.jar"]