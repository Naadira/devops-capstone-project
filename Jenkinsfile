pipeline {
    agent any

    tools {
        maven 'Maven 3.8.1' // Ensure Maven is installed
    }

    stages {
        stage('Clone Repository') {
            steps {
                git 'https://github.com/Naadira/devops-capstone-project.git'
            }
        }
        
        stage('Build with Maven') {
            steps {
                // Grant execute permission to mvnw
                sh 'chmod +x mvnw'
                sh './mvnw clean package' // Build the project
            }
        }
        
        stage('Build Docker Image') {
            steps {
                script {
                    // Build the Docker image and tag it with the build ID
                    docker.build("naadira/spring-boot-demo:${env.BUILD_ID}")
                }
            }
        }
        
        stage('Push Docker Image to Docker Hub') {
            steps {
                script {
                    docker.withRegistry('https://index.docker.io/v1/', 'dockerhub-credentials') {
                        // Push the tagged image to Docker Hub
                        docker.image("naadira/spring-boot-demo:${env.BUILD_ID}").push("latest")
                    }
                }
            }
        }
        
        stage('Deploy to EC2') {
            steps {
                sshagent(['ec2-ssh-key_1']) {
                    sh '''
                    ssh -o StrictHostKeyChecking=no ec2-user@ec2-3-128-182-162.us-east-2.compute.amazonaws.com "
                        # Stop and remove existing container if it exists
                        docker ps -q --filter "name=spring-boot-demo" | xargs -r docker stop
                        docker ps -aq --filter "name=spring-boot-demo" | xargs -r docker rm
                        
                        # Pull the latest image and run a new container
                        docker pull naadira/spring-boot-demo:${BUILD_ID}
                        docker run -d -p 8080:8080 --name spring-boot-demo naadira/spring-boot-demo:${BUILD_ID}
                    "
                    '''
                }
            }
        }
    }

    post {
        success {
            echo 'Deployment Successful!'
        }
        failure {
            echo 'Deployment Failed!'
        }
    }
}
