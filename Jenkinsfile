pipeline {
    agent any
    stages {
        stage("Permission") {
            steps {
                sh "chmod +x ./gradlew"
            }
        }
        stage("Test") {
            steps {
                sh "./gradlew test"
            }
        }
        stage("Code Coverage") {
            steps {
                sh "./gradlew jacocoTestCoverageVerification"
                sh "./gradlew jacocoTestReport"
                publishHTML (target: [
                    reportDir: 'build/reports/jacoco/test/html',
                    reportFiles: 'index.html',
                    reportName: 'JaCoCo Report'
                ])
            }
        }
        stage("Gradle Build") {
            steps {
                sh "./gradlew clean build"
            }
        }
        stage("Docker Build") {
            steps {
                sh "docker build -t 2024-calculator ."
            }
        }
        stage("Push Image") {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'docker_hub_test_credential',
                    usernameVariable: 'DOCKER_USERNAME',
                    passwordVariable: 'DOCKER_PASSWORD'
                )]) {
                    sh """
                    echo "$DOCKER_PASSWORD" | docker login -u "$DOCKER_USERNAME" --password-stdin
                    docker tag 2024-calculator:latest $DOCKER_USERNAME/2024-calculator:latest
                    docker push $DOCKER_USERNAME/2024-calculator:latest
                    docker logout
                    """
                }
            }
        }
    }
}