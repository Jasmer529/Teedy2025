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

                    if ! minikube status | grep -q "Running"; then
                        echo Starting Minikube...
                        minikube start
                    else
                        echo Minikube is already running.
                    fi
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
