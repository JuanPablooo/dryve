pipeline {
    agent any
    stages {
        stage('Build') {
            agent {
                docker {image 'maven'}
            }
            steps {
                sh "mvn package -Dmaven.test.skip=true"
            }
        }
        stage('Test') {
            agent {
                docker {image 'maven'}
            }
            steps {
                sh "mvn clean install"
            }
        }
    }
}
