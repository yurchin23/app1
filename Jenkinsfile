pipeline {
    agent any

    environment {
        DOCKER_REGISTRY = "localhost:5000"
        APP_NAME = "myapp"
        APP_PATH = "spring-boot-app/app0"
        APP_HELM_NAME = "app0"
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'master', url: 'https://github.com/yurchin23/app1.git'
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
                    sh 'docker build -t ${APP_NAME}:latest .'
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
                script {
                    // Установка helm, если требуется
                    // sh 'curl https://raw.githubusercontent.com/helm/helm/main/scripts/get-helm-3 | bash'
                    
                    // // Настройка kubeconfig
                    // sh "mkdir -p /home/jenkins/.kube"
                    // sh "cp /path/to/your/kubeconfig ${KUBE_CONFIG_PATH}" // Укажите путь к вашему kubeconfig
                    
                    // // Проверка доступа к Kubernetes
                    // sh "kubectl --kubeconfig=${KUBE_CONFIG_PATH} cluster-info"
                    
                    // Обновление или установка релиза с помощью Helm
                    dir("${APP_PATH}") {
                        sh "helm upgrade --install ${APP_HELM_NAME} . --set image.repository=${DOCKER_REGISTRY}/${APP_NAME} --set image.tag=latest"
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
