pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                sh 'chmod +x gradlew'
                sh './gradlew clean build'
            }
        }
        stage('Deploy') {
            steps {
                sh '''
                    # 기존 프로세스 종료
                    pid=$(pgrep -f jup-jup-0.0.1-SNAPSHOT.jar) || true
                    if [ -n "$pid" ]; then
                        echo "Stopping existing application"
                        kill $pid
                    fi

                    # 새 버전 실행
                    nohup java -jar build/libs/jup-jup-0.0.1-SNAPSHOT.jar > /dev/null 2>&1 &
                    echo "Application started"
                '''
            }
        }
    }
}