pipeline {
    agent any
    parameters {
        gitParameter name: 'TAG',
                     type: 'PT_TAG',
                     defaultValue: 'master',
                     description: 'Select the tag to deploy'
    }
    environment {
        DOCKER_REGISTRY = "localhost:5000"
        APP1_NAME = "app0"
        APP1_PATH = "spring-boot-app/app0"
        APP1_HELM_NAME = "app0"
        APP2_NAME = "app1"
        APP2_PATH = "spring-boot-app/app1"
        APP2_HELM_NAME = "app1"
        VERSION = "${params.TAG}"
    }

    stages {
        stage('Checkout') {
            steps {
                checkout([$class: 'GitSCM',
                          branches: [[name: "${params.TAG}"]],
                          doGenerateSubmoduleConfigurations: false,
                          extensions: [],
                          gitTool: 'Default',
                          submoduleCfg: [],
                          userRemoteConfigs: [[url: 'https://github.com/yurchin23/app1.git']]
                        ])
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

        stage('Deploy App1 with Helm') {
            when {
                expression { return currentBuild.currentResult == 'SUCCESS' }
            }
            steps {
                script {
                    dir("${APP2_PATH}") {
                        sh "helm upgrade ${APP2_HELM_NAME} . --set image.repository=${DOCKER_REGISTRY}/${APP2_NAME} --set image.tag=${VERSION}"
                    }
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
