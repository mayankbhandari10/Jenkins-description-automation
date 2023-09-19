def showDescription(Map config) {
    text='java.lang.ArithmeticException: Division by zero'
    keyword="ArithmeticException"
    
    def logs = config.logs
    def patterns = [
        'exception': 'Caused: java.io.IOException: Cannot run program "nohup"',
        'Error 2': 'script returned exit code 127',
        'not found': 'groovy.lang.MissingPropertyException: No such property: \\w+ for class: groovy.lang.Binding',
        'maths error': 'java.lang.ArithmeticException: Division by zero',
        'Assertation error': 'java.lang.AssertionError',
        'executor error': 'jenkins.util.ErrorLoggingExecutorService'
    ]

    def matchingLines = []
    //match operator
    logs.each { line ->
        patterns.each { name, regex ->
            if (line =~ regex) {
                matchingLines.add("Build Failure: ${name}\n${line}")
            }
        }
    }

    if (matchingLines.isEmpty()) {
        // If no patterns matched, provide a default message
        return "This build failed because of the following reasons:\nBuild is failed !! Please check the logs for reasons"
    } else {
        // If patterns matched, prepend the message to the matching lines
        return "This build failed because of the following reasons:\n${matchingLines.join('\n')}"
    }
    
}

def searchAndReturnMessage(line, keyword) {
    if (line.contains(keyword)) {
        matchingLines.add("Build Failure unit test case failed:${line}")
    } else {
        return "Keyword '$keyword' not found in the line."
    }
}
