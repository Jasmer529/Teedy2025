pipeline {
    agent any
    environment {
        // Jenkins credentials configuration
        DOCKER_HUB_CREDENTIALS = credentials('dockerhub-credentials') // DockerHub 凭证 ID

        // Docker Hub Repository 的名字
        DOCKER_IMAGE = 'jasmer529/Teedy2025'

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
                    // 假设 Dockerfile 位于项目根目录
                    docker.build("${env.DOCKER_IMAGE}:${env.DOCKER_TAG}")
                }
            }
        }

        stage('Push Image') {
            steps {
                script {
                    docker.withRegistry('https://registry.hub.docker.com', DOCKER_HUB_CREDENTIALS) {
                        // 推送带 tag 的镜像
                        docker.image("${env.DOCKER_IMAGE}:${env.DOCKER_TAG}").push()
                        // 可选：也推送 latest 标签
                        docker.image("${env.DOCKER_IMAGE}:${env.DOCKER_TAG}").push('latest')
                    }
                }
            }
        }

        stage('Run Container') {
            steps {
                script {
                    // 停止并删除已有容器（若存在）
                    bat 'docker stop teedy-container-8081 || exit 0'
                    bat 'docker rm teedy-container-8081 || exit 0'

                    // 启动新的容器
                    docker.image("${env.DOCKER_IMAGE}:${env.DOCKER_TAG}").run('--name teedy-container-8081 -d -p 8081:8080')

                    // 列出当前运行的 teedy 容器
                    bat 'docker ps --filter "name=teedy-container"'
                }
            }
        }
    }
}
