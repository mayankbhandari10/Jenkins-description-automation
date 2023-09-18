def call(Map patterns) {
    pipeline {
        agent any
        
        stages {
            stage('Build') {
                steps {
                    catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                        // This block captures errors during the build
                        echo "ERROR: This is an example error message"
                        error 'This is an intentional error'
                        sh 'echo "This stage has a syntax error"'
                    }
                }
            }
            stage('Excep') {
                steps {
                    catchError(buildResult: 'FAILURE', stageResult: 'FAILURE') {
                        // This block captures errors in the 'Excep' stage
                        sh 'your_command_that_may_produce_exceptions'
                    }
                }
            }
        }
        
        post {
            always {
                script {
                    // Collect the matching lines in a variable
                    def matchingLines = []

                    // Parse the log to find error messages and exceptions
                    def log = currentBuild.rawBuild.getLog(1000) // Adjust the number to read enough lines

                    log.each { line ->
                        // Check each pattern against the log line
                        patterns.each { name, regex ->
                            if (line =~ regex) {
                                matchingLines.add("Matching Pattern: ${name}\n${line}")
                            }
                        }
                    }

                    // Set the build description to the matching lines
                    if (matchingLines) {
                        currentBuild.description = matchingLines.join('\n')
                    }
                }
            }
        }
    }
}