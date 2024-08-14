pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/jup-jup/Back-end.git'
            }
        }
        stage('Build') {
            steps {
                sh 'gradle build'  // 또는 Maven을 사용한다면 'mvn clean package'
            }
        }
        stage('Deploy') {
            steps {
                sh 'java -jar build/libs/jup-jup-0.0.1-SNAPSHOT.jar'  // JAR 파일 실행 예시
            }
        }
    }
}