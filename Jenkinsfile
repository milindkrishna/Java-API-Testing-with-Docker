// v1
// pipeline {
//     agent {
//         label "slave"
//     }

//     stages {
//         stage('Clone from GIT') {
//             steps {
//                 echo 'clone the repository'
//                 git branch:'main', url:"https://github.com/milindkrishna/Java-API-Testing-with-Docker.git"
//             }
//         }
//         stage('Run the stage with docker container') {
//             steps {
//                 echo 'Run test inside container'
//                 // bat 'docker run milindkrishna/testjavaapidocker:v1.0'
//                 bat 'docker-compose up'
//             }
//         }
//         stage('Delete docker container') {
//             steps {
//                 echo 'Delete docker container'
//                 // bat 'docker system prune -af'
//                 bat 'docker-compose down'
//             }
//         } 
//         stage('Jenkins Pipeline completed') {
//             steps {
//                 echo 'Executed Succeessfully !!!!'
//                 bat 'docker images'
//             }
//         } 
//     }
// }

// v2
pipeline {
    agent {
        label 'slave'
    }
    
    stages {
        stage('Clone from GIT') {
            steps {
                echo 'clone the repository'
                git branch: 'main', url: "https://github.com/milindkrishna/Java-API-Testing-with-Docker.git"
            }
        }
        
        stage('Run the test') {
            steps {
                echo 'Run test inside container'
                bat '''
                    docker run --rm ^
                    -v "%cd%":/workspace ^
                    -w /workspace ^
                    milindkrishna/testjavaapidocker:v1.0 ^
                    mvn clean test
                '''
            }
        }
        
        stage('Jenkins Pipeline completed') {
            steps {
                echo 'Executed Successfully !!!!'
                bat 'docker images'
            }
        }
        
        stage('Delete docker container') {
            steps {
                echo 'Delete docker container'
                bat 'docker-compose down'
                bat 'docker system prune -af'
            }
        }
    }
}
