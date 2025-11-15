pipeline {
    agent {
        label "slave"
    }

    stages {
        stage('Clone from GIT') {
            steps {
                echo 'clone the repository'
                git branch:'main', url:"https://github.com/milindkrishna/Java-API-Testing-with-Docker.git"
            }
        }
        stage('Run the stage with docker container') {
            steps {
                echo 'Run test inside container'
                // bat 'docker run milindkrishna/testjavaapidocker:v1.0'
                bat 'docker-compose up'
            }
        }
        stage('Delete docker container') {
            steps {
                echo 'Delete docker container'
                // bat 'docker system prune -af'
                bat 'docker-compose down'
            }
        } 
        stage('Jenkins Pipeline completed') {
            steps {
                echo 'Executed Succeessfully !!!!'
                bat 'docker images'
            }
        } 
    }
}