def showDescription(Map config) {
    text = 'java.lang.ArithmeticException: Division by zero'
    keyword = "ArithmeticException"
    
    def logs = config.logs
    def patterns = [
        'maths error': 'java.lang.ArithmeticException: Division by zero',
        'Assertation error': 'java.lang.AssertionError',
        // Add more patterns here as needed
    ]

    def matchingLines = []

    logs.each { line ->
        patterns.each { name, regex ->
            if (line =~ regex) {
                def message = "Build Failure: ${name}\n${line}"
                def action = patternActions[name]
                if (action) {
                    message += "\n${action(line, keyword)}"
                }
                matchingLines.add(message)
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
        return "Build Failure unit test case failed: ${line}"
    } else {
        return "Keyword '${keyword}' not found in the line."
    }
}

// Define a map of pattern actions
def patternActions = [
    'maths error': { line, keyword -> searchAndReturnMessage(line, keyword) },
    // Add more pattern actions here as needed
]
