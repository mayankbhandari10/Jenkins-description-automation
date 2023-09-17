pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                sh 'make' // Example build step
            }
        }
        stage('Post-build Actions') {
            steps {
                script {
                    // Define Log Parser rules
                    def logParserRules = '''\
                        error /ERROR/
                        warning /WARNING/
                    '''

                    // Parse the build log using Log Parser
                    logParser(text: currentBuild.rawBuild.getLogReader('console'), ruleFile: [class: 'String', value: logParserRules], unstableOnWarning: true, failBuildOnError: true)
                }
            }
        }
    }
}
