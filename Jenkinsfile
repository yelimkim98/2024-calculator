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
        stage("Static Analysis") {
            steps {
                sh "./gradlew checkstyleMain"
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
            environment {
                CALCULATOR_CREDENTIAL = credentials('docker_hub_test_credential')
                TAG = "1.0.$BUILD_ID"
            }
            steps {
                sh "echo $CALCULATOR_CREDENTIAL_PSW | docker login -u $CALCULATOR_CREDENTIAL_USR --password-stdin"
                sh "echo \"# BUILD_ID = $BUILD_ID\""
                sh "echo \"# BUILD_NUMBER = $BUILD_NUMBER\""
                sh "docker tag 2024-calculator:latest $CALCULATOR_CREDENTIAL_USR/2024-calculator:$TAG"
                sh "docker push $CALCULATOR_CREDENTIAL_USR/2024-calculator:$TAG"
                sh "docker logout"
//                 withCredentials([usernamePassword(
//                     credentialsId: 'docker_hub_test_credential',
//                     usernameVariable: 'DOCKER_USERNAME',
//                     passwordVariable: 'DOCKER_PASSWORD'
//                 )]) {
//                     sh """
//                     echo "$DOCKER_PASSWORD" | docker login -u "$DOCKER_USERNAME" --password-stdin
//                     docker tag 2024-calculator:latest $DOCKER_USERNAME/2024-calculator:latest
//                     docker push $DOCKER_USERNAME/2024-calculator:latest
//                     docker logout
//                     """
//                 }
            }
        }
    }
}