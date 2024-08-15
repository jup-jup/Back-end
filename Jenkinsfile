pipeline {
    agent any
    environment {
        PROJECT_ID = 'jupjup'
        LOCATION = 'asia-northeast3-c'
        CREDENTIALS_ID = 'gcp-jupjup-credentials'  // Jenkins에서 설정한 크리덴셜 ID
        INSTANCE_NAME = 'jupjup'  // GCE 인스턴스 이름
    }
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Build') {
            steps {
                sh './gradlew clean build'
            }
        }
        stage('Build and Push Docker Image') {
            steps {
                script {
                    def appName = "jupjup"
                    def imageTag = "gcr.io/${PROJECT_ID}/${appName}:${env.BUILD_NUMBER}"

                    // Authenticate with GCP
                    step([$class: 'GoogleContainerRegistryCredentialStep',
                          credentialsId: CREDENTIALS_ID,
                          projectId: PROJECT_ID])

                    // Build the Docker image
                    sh "docker build -t ${imageTag} ."

                    // Push the image to GCR
                    sh "docker push ${imageTag}"
                }
            }
        }
        stage('Deploy to GCE') {
            steps {
                script {
                    // Deploy to GCE instance
                    sh """
                        gcloud compute ssh ${INSTANCE_NAME} --zone ${LOCATION} --project ${PROJECT_ID} --command="
                        docker pull ${imageTag} &&
                        docker stop jupjup-container || true &&
                        docker rm jupjup-container || true &&
                        docker run -d --name jupjup-container -p 80:8080 ${imageTag}"
                    """
                }
            }
        }
    }
    post {
        always {
            cleanWs()
        }
    }
}