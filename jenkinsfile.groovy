pipeline {
    agent any
    parameters {
        string(name: 'GREETING', defaultValue: 'Hello', description: 'A greeting message')
        choice(name: 'ENVIRONMENT', choices: ['DEV', 'QA', 'PROD'], description: 'Select the environment')
        booleanParam(name: 'DEPLOY', defaultValue: true, description: 'Deploy after build?')
    }
    stages {
        stage('Build') {
            steps {
                script {
                    echo "Greeting: ${params.GREETING}"
                    echo "Environment: ${params.ENVIRONMENT}"
                    if (params.DEPLOY) {
                        echo "Deploying..."
                    } else {
                        echo "Skipping deployment."
                    }
                }
            }
        }
    }
}
