pipeline {
    agent any

    environment {
        DOCKER_REGISTRY = "localhost:5000"
        APP_NAME = "myapp"
        APP_PATH = "spring-boot-app/app0"
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/yurchin23/app1.git'
            }
        }

        stage('Build Maven Project') {
            steps {
                dir("${APP_PATH}") {
                    sh 'mvn clean install'
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                dir("${APP_PATH}") {
                    sh 'docker build . -t ${APP_NAME}'
                }
            }
        }

        stage('Tag Docker Image') {
            steps {
                sh 'docker tag ${APP_NAME}:latest ${DOCKER_REGISTRY}/${APP_NAME}:latest'
            }
        }

        stage('Push Docker Image to Registry') {
            steps {
                sh 'docker push ${DOCKER_REGISTRY}/${APP_NAME}:latest'
            }
        }

        stage('Deploy with Helm') {
            steps {
                dir("${APP_PATH}") {
                    sh 'helm upgrade --install ${APP_NAME} . --set image.repository=${DOCKER_REGISTRY}/${APP_NAME} --set image.tag=latest'
                }
            }
        }
    }

    post {
        success {
            echo 'Deployment successful!'
        }
        failure {
            echo 'Deployment failed!'
        }
    }
}
