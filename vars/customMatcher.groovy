

def call(Map config) {
    def logs = config.logs
    def patterns = [
        'exception': 'Caused: java\\.io\\.IOException: Cannot run program "nohup"',
        'Error 2': 'error_pattern2',
        'not found': 'groovy\.lang\.MissingPropertyException: No such property: \w+ for class: groovy\.lang\.Binding'
        //o Add more patterns as needed
    ]

    def matchingLines = []

    logs.each { line ->
        patterns.each { name, regex ->
            if (line =~ regex) {
                matchingLines.add("Matching Pattern: ${name}\n${line}")
            }
        }
    }

    return matchingLines.join('\n')
}
