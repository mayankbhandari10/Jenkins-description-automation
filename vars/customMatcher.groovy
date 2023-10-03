def showDescription() {
    def logs = retrieveLogs()
    
    def buildStatus = retrieveBuildStatus()
    echo"buildStatus"
    echo buildStatus

    def patterns = [
        'Artifactory (TLS handshake) issue': 'curl: (35) Encountered end of file',
        'Artifactory timeout': 'artifactory.kod.kyriba.com.*: net/http: request canceled.*Client.Timeout exceeded while awaiting headers',
        'Cannot checkout - falling back': 'falling back to nondeterministic checkout',
        'Cannot checkout - maximum retry': 'ERROR: Maximum checkout retry attempts reached, aborting',
        'Cannot checkout - No property': 'ERROR: Caught error: No such property: branches for class: hudson.scm.NullSCM',
        'Cannot checkout - Remote branch': 'fatal: Remote branch .* not found in upstream origin',
        // Add more patterns here...
        'Unit test failed': 'java.lang.ArithmeticException: Division by zero'
    ]

    def matchingLines = []
    logs.each { line ->
        patterns.each { name, regex ->
            if (line =~ regex) {
                matchingLines << "Build Failure: $name\n$line"
            }
        }
    }

    if (matchingLines.isEmpty()) {
        return "This build failed because of the following reasons:\nBuild failed!! Please check the logs for reasons."
    } else {
        return "This build failed because of the following reasons:\n${matchingLines.join('\n')}"
    }
}

def retrieveLogs() {
    return currentBuild.rawBuild.getLog(10000).reverse()
}

def retrieveBuildStatus() {
    return currentBuild.resultIsWorseOrEqualTo('FAILURE') ? 'FAILURE' : 'SUCCESS'
}
