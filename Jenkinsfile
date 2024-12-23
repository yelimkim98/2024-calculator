pipeline {
    agent any
    environment {
        DOCKER_HUB_IMAGE_REPO =  "kiel0103/2024-calculator"
        TAG = "1.0.${env.BUILD_ID}"
    }
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
            }
            steps {
                sh "echo $CALCULATOR_CREDENTIAL_PSW | docker login -u $CALCULATOR_CREDENTIAL_USR --password-stdin"
                sh "docker tag 2024-calculator:latest $DOCKER_HUB_IMAGE_REPO:$TAG"
                sh "docker push $DOCKER_HUB_IMAGE_REPO:$TAG"
                sh "docker logout"
            }
        }
        stage("Deploy") {
            steps {
                sh "docker run -d -p 8765:8080 $DOCKER_HUB_IMAGE_REPO:$TAG"
            }
        }
        stage("Acceptance Test") {
            steps {
                sleep 120
                sh "chmod +x acceptance-test.sh && ./acceptance-test.sh"
            }
        }
    }
    post {
        always {    // stages 에서 중간에 실패하더라도 무조건 수행됨
            echo "finalize"

            // '' 사용 시 $ 부분이 Groovy 변수로 인식되지 않음
            sh 'docker stop $(docker ps -q)'
            sh 'docker rm $(docker ps -aq)'
            sh "docker rmi $DOCKER_HUB_IMAGE_REPO:$TAG"

            emailext(
                body: "${env.JOB_NAME} - Build # ${env.BUILD_NUMBER} - ${currentBuild.currentResult}: Check console output at ${env.BUILD_URL} to view the results.",
                subject: "${env.JOB_NAME} - Build #${env.BUILD_NUMBER} - ${currentBuild.currentResult}",
                to: "kiel0103@naver.com,kiel0103@kakao.com"
            )
        }
    }
}