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
                    echo Minikube is already running.
                '''
            }
        }
        stage('Set Image') {
            steps {
                bat '''
                    echo Setting image for deployment...

                '''
            }
        }
        stage('Verify') {
            steps {
                bat '''
                    echo Verifying deployment...
                '''
            }
        }
    }
}