def showDescription(Map config) {
    def logs = config.logs
    def buildStatus = config.buildStatus

    // Check if the build status is FAILURE
    if (buildStatus == 'FAILURE') {
        def patterns = [
            //'exception': 'Caused: java.io.IOException: Cannot run program "nohup"',
            //'Error 2': 'script returned exit code 127',
            //'not found': 'groovy.lang.MissingPropertyException: No such property: \\w+ for class: groovy.lang.Binding',
            'Unit test failed': 'java.lang.ArithmeticException: Division by zero',
            //'Assertation error': 'java.lang.AssertionError',
            //'executor error': 'jenkins.util.ErrorLoggingExecutorService',
            'Unit test failed': 'Execution failed for task'
            // Add more patterns as needed
        ]

        def matchingLines = []
        logs.each { line ->
            patterns.each { name, regex ->
                if (line =~ regex) {
                    matchingLines.add("Build Failure: ${name}\n${line}")
                }
            }
        }

        if (matchingLines.isEmpty()) {
            return "This build failed because of the following reasons:\nBuild is failed !! Please check the logs for reasons"
        } else {
            return "This build failed because of the following reasons:\n${matchingLines.join('\n')}"
        }
    } else {
        // If the build status is not FAILURE, return an empty string
        return ""
    }
}

// Example usage:
// Call showDescription with the build status and logs
def description = showDescription(buildStatus: currentBuild.resultIsWorseOrEqualTo('FAILURE') ? 'FAILURE' : 'SUCCESS', logs: currentBuild.rawBuild.getLog(10000).reverse())
println(description)
