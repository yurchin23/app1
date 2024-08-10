pipeline {
    agent any

    environment {
        DOCKER_REGISTRY = "localhost:5000"
        APP1_NAME = "app0"
        APP1_PATH = "spring-boot-app/app0"
        APP1_HELM_NAME = "app0"
        APP2_NAME = "app1"
        APP2_PATH = "spring-boot-app/app1"
        APP2_HELM_NAME = "app1"
        VERSION = "${env.TAG}"
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'master', url: 'https://github.com/yurchin23/app1.git'
            }
        }

        stage('Build Maven Project for App0') {
            steps {
                dir("${APP1_PATH}") {
                    sh 'mvn clean install'
                }
            }
        }

        stage('Build Docker Image for App0') {
            steps {
                dir("${APP1_PATH}") {
                    sh 'docker build -t ${APP1_NAME}:latest .'
                }
            }
        }

        stage('Tag Docker Image for App0') {
            steps {
                sh 'docker tag ${APP1_NAME}:latest ${DOCKER_REGISTRY}/${APP1_NAME}:${VERSION}'
            }
        }

        stage('Push Docker Image for App0 to Registry') {
            steps {
                sh 'docker push ${DOCKER_REGISTRY}/${APP1_NAME}:${VERSION}'
            }
        }

        stage('Update Helm Values and Push') {
            steps {
                script {
                    dir("${APP1_PATH}") {
                        // Обновляем values.yaml
                        sh "sed -i 's/tag: .*/tag: ${VERSION}/' values.yaml"
                        sh 'git add values.yaml'
                        sh 'git commit -m "Update values.yaml for ${APP1_NAME} to version ${VERSION}"'
                        sh 'git push origin master'
                    }
                }
            }
        }

        stage('Deploy App0 with Helm') {
            steps {
                script {
                    dir("${APP1_PATH}") {
                        sh "helm upgrade ${APP1_HELM_NAME} . --set image.repository=${DOCKER_REGISTRY}/${APP1_NAME} --set image.tag=${VERSION}"
                    }
                }
            }
        }

        stage('Build Maven Project for App1') {
            when {
                expression { return currentBuild.currentResult == 'SUCCESS' }
            }
            steps {
                dir("${APP2_PATH}") {
                    sh 'mvn clean install'
                }
            }
        }

        stage('Build Docker Image for App1') {
            when {
                expression { return currentBuild.currentResult == 'SUCCESS' }
            }
            steps {
                dir("${APP2_PATH}") {
                    sh 'docker build -t ${APP2_NAME}:latest .'
                }
            }
        }

        stage('Tag Docker Image for App1') {
            when {
                expression { return currentBuild.currentResult == 'SUCCESS' }
            }
            steps {
                sh 'docker tag ${APP2_NAME}:latest ${DOCKER_REGISTRY}/${APP2_NAME}:${VERSION}'
            }
        }

        stage('Push Docker Image for App1 to Registry') {
            when {
                expression { return currentBuild.currentResult == 'SUCCESS' }
            }
            steps {
                sh 'docker push ${DOCKER_REGISTRY}/${APP2_NAME}:${VERSION}'
            }
        }

        stage('Update Helm Values and Push') {
            when {
                expression { return currentBuild.currentResult == 'SUCCESS' }
            }
            steps {
                script {
                    dir("${APP2_PATH}") {
                        // Обновляем values.yaml
                        sh "sed -i 's/tag: .*/tag: ${VERSION}/' values.yaml
