pipeline {
    agent any
    environment {
        DEPLOYMENT_NAME = "hello-node"
        CONTAINER_NAME = "docs"
        IMAGE_NAME = "jasmer529/teedy2025:latest"
    }
    stages {
        stage('Start Minikube') {
            steps {
                bat '''
                    echo Setting PATH...
                    SET "PATH=C:\\Users\\Administrator\\scoop\\shims;%PATH%"

                    echo Setting minikube driver to docker...
                    minikube config set driver docker

                    echo Checking minikube status...
                    minikube status | findstr /C:"Running"
                    if %errorlevel% neq 0 (
                        echo Minikube not running, starting with docker driver...
                        minikube start --driver=docker --force
                    ) else (
                        echo Minikube already running.
                    )
                '''
            }
        }
        stage('Set Image') {
            steps {
                bat '''
                    echo Setting image for deployment...
                    kubectl set image deployment/%DEPLOYMENT_NAME% %CONTAINER_NAME%=%IMAGE_NAME%
                '''
            }
        }
        stage('Verify') {
            steps {
                bat 'kubectl rollout status deployment/%DEPLOYMENT_NAME%'
                bat 'kubectl get pods'
            }
        }
    }
}
