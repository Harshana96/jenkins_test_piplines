pipeline {
    agent any

    environment {
        // Define any environment variables here
        GIT_BRANCH = 'main'  // Replace with your branch name
        BUILD_DIR = 'build'  // Example build directory
    }

    stages {
        stage('Checkout') {
            steps {
                script {
                    // Checkout the code from the repository
                    git branch: "${GIT_BRANCH}", url: 'https://your-repository-url.git'
                }
            }
        }

        stage('Install Dependencies') {
            steps {
                script {
                    // Install dependencies (e.g., Node.js, Python, Maven, etc.)
                    sh 'npm install'  // Modify this based on your environment
                }
            }
        }

        stage('Build') {
            steps {
                script {
                    // Build your project (e.g., using npm, Maven, Gradle, etc.)
                    sh 'npm run build'  // Modify based on your build tool
                }
            }
        }

        stage('Test') {
            steps {
                script {
                    // Run tests (e.g., unit tests, integration tests, etc.)
                    sh 'npm test'  // Modify based on your testing framework
                }
            }
        }

        stage('Lint') {
            steps {
                script {
                    // Run linting to check for code quality issues
                    sh 'npm run lint'  // Modify based on your linting tool
                }
            }
        }

        stage('Security Check') {
            steps {
                script {
                    // Run security checks (e.g., using tools like Snyk, OWASP Dependency-Check)
                    sh 'snyk test'  // Modify based on the security tools you use
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    // Build Docker image for the application
                    sh 'docker build -t your-image-name .'
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                script {
                    // Push the Docker image to a Docker registry
                    sh 'docker push your-image-name'
                }
            }
        }

        stage('Deploy to Staging') {
            steps {
                script {
                    // Deploy the built image to a staging environment
                    sh 'kubectl apply -f kubernetes/staging-deployment.yaml'  // Modify as per your deployment method
                }
            }
        }

        stage('Deploy to Production') {
            steps {
                script {
                    // Deploy the built image to production environment
                    input message: 'Approve deployment to Production?', ok: 'Deploy'
                    sh 'kubectl apply -f kubernetes/production-deployment.yaml'  // Modify as per your deployment method
                }
            }
        }
    }

    post {
        always {
            // Clean up, send notifications, or other post-job tasks
            cleanWs()
        }
        success {
            // Actions to take on a successful pipeline run (e.g., send success notification)
            echo 'Build and deployment successful!'
        }
        failure {
            // Actions to take on a failed pipeline run (e.g., send failure notification)
            echo 'Build or deployment failed!'
        }
    }
}
