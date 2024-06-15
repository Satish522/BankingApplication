pipeline {
    agent any
    tools{
        maven 'Maven'
    }
    environment {
        DOCKER_REGISTRY = 'registry-1.docker.io/satish2121'
        DOCKER_CREDENTIALS_ID = 'docker-hub-credentials'
    }
    
    stages {
        stage('Maven Build') {
            steps {
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/Satish522/BankingApplication.git']])
                script {
                    dir('accounts') {
                        // Build the Docker image for the accounts microservice using Jib
                        sh 'mvn compile jib:build'
                    }
                    dir('cards') {
                        sh 'mvn compile jib:build'
                    }
                    dir('loans') {
                        sh 'mvn compile jib:build'
                    }
                    dir('configserver') {
                        sh 'mvn compile jib:build'
                    }
                    
                }
            }
        }
        stage('Docker Compose Run') {
            steps{
                script {
                    dir('docker-compose/default') {
                        sh 'docker compose up -d'
                    }
                }
            }
        }
        
    }
}
