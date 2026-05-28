pipeline {
  agent any
triggers {
   pollscm ('H/1 * * * *')
}
environment {
    DOCKER_IMAGE = "ayushforyou/mvproject"
    CONTAINER_NAME = "springboot-container"
}

stages {
      stage('checkout')
{
 steps{
git branch : 'main', url: "https://github.com/ayush4u-cpu/maven-project.git"
}
}
stage('build JAR')
{
  steps {
   sh 'chmod +x mvnw'
   sh './mvnw clean package -DskipTests'
}
}
stage('build docker image')
{
steps {
   sh 'docker build -t $DOCKER_IMAGE:latest'
}
}
stage('docker login')
{
steps
{
   withCredentials([usernamePassword(
     credentialsID : 'dockerhub-credentials',
     usernameVariable: 'DOCKER_USER',
     passwordvariable: 'DOCKER_PASS'
   )]) {
     sh 'echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin'
}
}
}

stage ('push image to dockerhub') {
   steps {
     sh 'docker push $DOCKER_IMAGE:latest'
}
}

stage('Run container')
{
steps {
  sh 'docker rm -f $CONTAINER_NAME || true'
  sh 'docker run -d --name $CONTAINER_NAME -p 3000:3000 $DOCKER_IMAGE:latest'
}
}

stage ('test application')
{
steps {
   sh 'curl http://localhost:3000/index.html'
}
}
}
}
   
