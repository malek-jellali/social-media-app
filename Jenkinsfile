pipeline {
    tools {
        jdk 'jdk17'
        maven 'maven3'
    }
    environment {
        repository = 'malekjellali1/social-media-app'
        springAppImage = "${repository}:latest" 
        IMAGE_TAG = 'latest'  
    }
    agent any

    stages {
        stage('CHECKOUT GIT') {
            steps {
                git(
                    branch: 'main',
                    url: 'https://github.com/malek-jellali/social-media-app.git',
                    credentialsId: 'github-cred'
                )
            }
        }

        stage('MVN CLEAN') {
            steps {
                sh 'mvn clean'
            }
        }
        
        stage('UNIT TESTS') {
            steps {
                echo 'Launching Unit Tests...'
                sh 'mvn test'
            }
        }
        
        stage('ARTIFACT CONSTRUCTION') {
            steps {
                echo 'ARTIFACT CONSTRUCTION...'
                sh 'mvn package'
            }
        }

        stage('SONARQUBE ANALYSIS') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'sonar-cred', usernameVariable: 'SONAR_USER', passwordVariable: 'SONAR_PASS')]) {
                    sh 'mvn sonar:sonar -Dsonar.projectKey=api-gateway -Dsonar.host.url=http://sonarqube:9000 -Dsonar.login=$SONAR_USER -Dsonar.password=$SONAR_PASS'
                }
            }
        }

        stage('PUBLISH TO NEXUS') {
            steps {
                withMaven(globalMavenSettingsConfig: 'config-settings', jdk: 'jdk17', maven: 'maven3', mavenSettingsConfig: '', traceability: true) {
                    sh 'mvn deploy'
                }
            }
        }

        stage('BUILD AND DEPLOY DOCKER COMPOSE') {
            steps {
                echo "Using Docker Image: ${springAppImage}"
                sh """
                    export IMAGE_TAG=latest  # Explicitly define the IMAGE_TAG for Docker Compose
                    SPRING_APP_IMAGE=${springAppImage} docker-compose -f docker-compose.yml up -d --build
                """
            }
        }

        stage('PUSH DOCKER IMAGE') {
            steps {
                echo "Pushing Docker Image: ${springAppImage}"
                script {
                    withDockerRegistry(credentialsId: 'docker-cred', url: 'https://index.docker.io/v1/') {
                        sh "docker push ${springAppImage}"
                    }
                }
            }
        }

        stage('DEPLOY TO MINIKUBE') {
            steps {
                script {
                    withEnv(["KUBECONFIG=/var/jenkins_home/.kube/config"]) {
                        sh 'kubectl config use-context minikube'
                        sh'kubectl apply -f secret.yml' 
                         sh 'kubectl apply -f mysql-manifest.yml'
                          sh 'kubectl apply -f app-manifest.yml'
                           sh'kubectl apply -f horizontal-autoscaler.yml'
                    }
                }
            }
        }
    }

    post {
        always {
            script {
                def jobName = env.JOB_NAME
                def buildNumber = env.BUILD_NUMBER
                def pipelineStatus = currentBuild.result ?: 'UNKNOWN'
                def bannerColor = pipelineStatus.toUpperCase() == 'SUCCESS' ? 'green' : 'red'

                def body = """
                    <html>
                    <body>
                    <div style="border: 4px solid ${bannerColor}; padding: 10px;">
                    <h2>${jobName} - Build ${buildNumber}</h2>
                    <div style="background-color: ${bannerColor}; padding: 10px;">
                    <h3 style="color: white;">API Gateway Build Status: ${pipelineStatus.toUpperCase()}</h3>
                    </div>
                    <p>The build and setup for the API Gateway was ${pipelineStatus.toUpperCase().toLowerCase()}.</p>
                    <p>For more details, check the <a href="${BUILD_URL}">console output</a>.</p>
                    </div>
                    </body>
                    </html>
                """

                emailext (
                    subject: "${jobName} - API Gateway Build ${buildNumber} - ${pipelineStatus.toUpperCase()}",
                    body: body,
                    to: 'malakjellali29@gmail.com',
                    from: 'jenkins@example.com',
                    replyTo: 'jenkins@example.com',
                    mimeType: 'text/html'
                )
            }
        }
    }
}
