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
                sh "docker tag 2024-calculator:latest $CALCULATOR_CREDENTIAL_USR/2024-calculator:$TAG"
                sh "docker push $CALCULATOR_CREDENTIAL_USR/2024-calculator:$TAG"
                sh "docker logout"
            }
        }
    }
    post {
        success {
            emailext(
                body: 'BUILD_ID=$BUILD_ID - Build Success',
                subject: 'Build Success',
                from: 'kiel0103@naver.com',
                to: 'kiel0103@naver.com'
            )
        }
        failure {
            emailext(
                body: 'BUILD_ID=$BUILD_ID - Build Failed',
                subject: 'Build Failed',
                from: 'kiel0103@naver.com',
                to: 'kiel0103@naver.com'
            )
        }
    }
}