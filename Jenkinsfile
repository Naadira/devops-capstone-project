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
                    docker.build("spring-boot-demo:${env.BUILD_ID}")
                }
            }
        }
        
        stage('Push Docker Image to Docker Hub') {
            steps {
                script {
                    docker.withRegistry('https://index.docker.io/v1/', 'dockerhub-credentials') {
                        // Tag the image with the correct format
                        docker.image("spring-boot-demo:${env.BUILD_ID}").tag("naadira/spring-boot-demo:latest")
                        // Push the tagged image to Docker Hub
                        docker.image("naadira/spring-boot-demo:latest").push()
                    }
                }
            }
        }
        
        stage('Deploy to EC2') {
            steps {
                sshagent(['ec2-ssh-key']) {
                    sh '''
                    ssh -i "devops-project.pem" ec2-user@ec2-3-128-182-162.us-east-2.compute.amazonaws.com "docker pull naadira/spring-boot-demo:latest && docker run -d -p 8080:8080 naadira/spring-boot-demo:latest"
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
