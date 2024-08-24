FROM openjdk:17-jdk-slim

# 작업 디렉토리 설정
WORKDIR /app

# JAR 파일 복사
COPY build/libs/*.jar app.jar

# 환경 변수 설정
# 서버 설정
ENV SERVER_PORT=8080

# MySQL 데이터베이스 설정
ENV MYSQL_URL=jdbc:mysql://localhost:3306/jupjup?serverTimezone=Asia/Seoul
ENV MYSQL_USER=root
ENV MYSQL_PASSWORD=jupjup1234

# 구글 OAuth2 설정
ENV GOOGLE_CLIENT_ID=541126883288-3qnon1d9hnllfaho31opp2kbaleaifg1.apps.googleusercontent.com
ENV GOOGLE_CLIENT_SECRET=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
ENV GOOGLE_REDIRECT_URI=http://localhost:8080/login/oauth2/code/google

# 네이버 OAuth2 설정
ENV NAVER_CLIENT_ID=P5rU2N4nihmGgZW9cU08
ENV NAVER_CLIENT_SECRET=EP1mnn2SSo
ENV NAVER_REDIRECT_URI=http://localhost:8080/login/oauth2/code/naver

# 카카오 OAuth2 설정
ENV KAKAO_CLIENT_ID=86cf25712d4c66f5fbd8dc40b7eae343
# ENV KAKAO_CLIENT_SECRET=xYbgONC3rGbArKh5nM20YIPXI0pv4eyj  # 주석 처리된 상태
ENV KAKAO_REDIRECT_URI=http://localhost:8080/login/oauth2/code/kakao

# JWT 설정
ENV JWT_SECRET=your_jwt_secret_here@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
ENV JWT_REFRESH_SECRET=your_jwt_refresh_secret_here@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

# 포트 노출
EXPOSE 8080

# 컨테이너 시작 시 실행할 명령어
ENTRYPOINT ["java", "-jar", "app.jar"]