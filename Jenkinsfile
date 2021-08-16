pipeline{
    agent any
    tools{
        maven 'Maven3'
    }
    environment{
        registry = "dikshasingla/nagp-inventory"
        username = "dikshasingla"
        docker_port = null
        container_name = "c-${username}-${BRANCH_NAME}"
        container_exist = "${bat(script:"docker ps -a -q -f name=${env.container_name}",returnStdout:true).trim().readLines().drop(1).join("")}"
    }
    stages{
        stage('checkout'){
            steps{
                echo "checking out from git repository for ${BRANCH_NAME}"
                checkout scm
				script{
					if(BRANCH_NAME == 'feature'){
						docker_port = 7400
					} else{
						docker_port = 7200
					}
				}
            }
        }
		stage('build'){
			steps{
				echo "code build"
				bat "mvn clean compile"
			}
		}
		stage('test cases execution'){
			steps{
				echo "running test cases"
				bat "mvn test"
			}
		}
		stage('sonarqube analysis'){
            steps{
                echo "running sonar qube analysis"
                withSonarQubeEnv(installationName:'Test_Sonar'){
                    bat "mvn clean install sonar:sonar -Dsonar.projectKey=nagp-inventory -Dsonar.name=nagp-inventory -Dsonar.login=a5fd037563e90cf38a6352c0926ecf89f40681d9"
                }
            }
		}
        stage('Docker image build'){
            steps{
                echo "building docker image"
                bat "mvn install"
                bat "docker build -t i-${username}-${BRANCH_NAME}:${BUILD_NUMBER} --no-cache -f Dockerfile ."
            }
        }
        stage('Push to docker hub'){
            steps{
                echo "pushing to docker hub"
                bat "docker tag i-${username}-${BRANCH_NAME}:${BUILD_NUMBER} ${registry}:${BUILD_NUMBER}-${BRANCH_NAME}"
                bat "docker tag i-${username}-${BRANCH_NAME}:${BUILD_NUMBER} ${registry}:latest-${BRANCH_NAME}"
                withDockerRegistry(credentialsId:'DockerHub',url:''){
                    bat "docker push ${registry}:${BUILD_NUMBER}-${BRANCH_NAME}"
                    bat "docker push ${registry}:latest-${BRANCH_NAME}"
                }
            }
        }
        stage('Deployment'){
            steps{
                parallel(
                    "Docker Deployment":{
                        script{
                            echo "check if container already exist on port 7400 with container-id = ${env.container_exist}"
                            if(env.container_exist != null){
                                echo "container already exist with name ${env.container_name}"
                                bat "docker stop ${env.container_name} && docker rm ${env.container_name}"
                            }
                            echo "deploying to docker"
                            bat "docker run --name ${env.container_name} -d -p ${docker_port}:3515 ${registry}:${BUILD_NUMBER}-${BRANCH_NAME}"
                        }
                    },
                    "Kubernetes Deployment":{
                        script{
                            echo "deploying to kubernetes"
                            bat "kubectl apply -f deployment.yaml"
                            bat "kubectl apply -f service.yaml"
                        }
                    }
                )
            }
        }
    }
}