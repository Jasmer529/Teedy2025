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
                sh '''
                    echo "Checking Minikube status..."
                    if ! minikube status | grep -q "host: Running"; then
                        echo "Starting Minikube..."
                        minikube start
                    else
                        echo "Minikube is already running."
                    fi
                '''
            }
        }

        stage('Set Image') {
            steps {
                sh '''
                    echo "Setting image for deployment..."
                    kubectl set image deployment/$DEPLOYMENT_NAME $CONTAINER_NAME=$IMAGE_NAME
                '''
            }
        }

        stage('Verify') {
            steps {
                sh '''
                    echo "Verifying deployment..."
                    kubectl rollout status deployment/$DEPLOYMENT_NAME
                    echo "Current pods:"
                    kubectl get pods
                '''
            }
        }
    }
}
