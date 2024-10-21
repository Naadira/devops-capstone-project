pipeline {
    agent any

    tools {
        maven 'Maven 3.8.1' // Ensure Maven is installed
    }

    stages {
        stage('Clone Repository') {
            steps {
                // Clone the Git repository
                git 'https://github.com/Naadira/devops-capstone-project.git'
            }
        }
        
        stage('Build with Maven') {
            steps {
                sh 'mvn clean package' // Build the application
            }
        }
        
        stage('Build Docker Image') {
            steps {
                script {
                    // Build the Docker image and tag it as 'latest'
                    docker.build("naadira/spring-boot-demo:latest")
                }
            }
        }
        
        stage('Push Docker Image to Docker Hub') {
            steps {
                script {
                    // Log in to Docker Hub and push the tagged image
                    docker.withRegistry('https://index.docker.io/v1/', 'dockerhub-credentials') {
                        // Push the image with the 'latest' tag
                        docker.image("naadira/spring-boot-demo:latest").push("latest")
                    }
                }
            }
        }

        stage('Deploy to EC2') {
            steps {
                sshagent(['ec2-ssh-key_2']) {
                    sh '''
                    ssh -o StrictHostKeyChecking=no ec2-user@ec2-13-59-239-221.us-east-2.compute.amazonaws.com "
                        docker pull naadira/spring-boot-demo:latest && 
                        docker stop spring-boot-demo || true && 
                        docker rm spring-boot-demo || true && 
                        docker run -d --name spring-boot-demo -p 8989:8000 naadira/spring-boot-demo:latest"
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
