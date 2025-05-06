pipeline {
    agent any
    environment {
        // Docker Hub Repository 的名字
        DOCKER_IMAGE = 'jasmer529/teedy2025'

        // 使用 Jenkins 的构建号作为 tag
        DOCKER_TAG = "${env.BUILD_NUMBER}"
    }

    stages {
        stage('Build') {
            steps {
                checkout scmGit(
                    branches: [[name: '*/master']],
                    extensions: [],
                    userRemoteConfigs: [[url: 'https://github.com/Jasmer529/Teedy2025.git']]
                )
                bat 'mvn -B -DskipTests clean package'
            }
        }

        stage('Build Image') {
            steps {
                script {
                    docker.build("${env.DOCKER_IMAGE}:${env.DOCKER_TAG}")
                }
            }
        }

        stage('Push Image') {
            steps {
                script {
                    docker.withRegistry('https://registry.hub.docker.com', 'dockerhub-credentials') {
                        docker.image("${env.DOCKER_IMAGE}:${env.DOCKER_TAG}").push()
                        docker.image("${env.DOCKER_IMAGE}:${env.DOCKER_TAG}").push('latest')
                    }
                }
            }
        }

        stage('Run Container') {
            steps {
                script {
                    bat 'docker stop teedy-container-8081 || exit 0'
                    bat 'docker rm teedy-container-8081 || exit 0'

                    docker.image("${env.DOCKER_IMAGE}:${env.DOCKER_TAG}").run('--name teedy-container-8081 -d -p 8081:8080')
                    bat 'docker ps --filter "name=teedy-container"'
                }
            }
        }
    }
}
