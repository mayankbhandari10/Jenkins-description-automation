def call(Map config) {
    def logs = config.logs
    def patterns = [
        'exception': 'Caused: java.io.IOException: Cannot run program "nohup"',
        'Error 2': 'script returned exit code 127',
        'not found': 'groovy.lang.MissingPropertyException: No such property: \\w+ for class: groovy.lang.Binding',
        'maths error': 'java.lang.ArithmeticException: Division by zero',
        'Assertation error': 'java.lang.AssertionError',
        'executor error': 'jenkins.util.ErrorLoggingExecutorService'
    ]

    // Add more patterns as needed

    def matchingLines = []

    logs.each { line ->
        patterns.each { name, regex ->
            if (line =~ regex) {
                matchingLines.add("Matching Pattern: ${name}\n${line}")
            }
        }
    }

    if (matchingLines.isEmpty()) {
        // If no patterns matched, provide a default message
        return "Build is failed !! Please check the logs for reasons"
    } else {
        return matchingLines.join('\n')
    }
}
