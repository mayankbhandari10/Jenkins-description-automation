def showDescription() {
    def logs = retrieveLogs()
    def buildStatus = retrieveBuildStatus()

    def patterns = [
        'Artifactory (TLS handshake) issue': 'curl: (35) Encountered end of file',
        'Artifactory timeout': 'artifactory.kod.kyriba.com.*: net/http: request canceled.*Client.Timeout exceeded while awaiting headers',
        'Cannot checkout': 'falling back to nondeterministic checkout',
        'Cannot checkout': 'ERROR: Maximum checkout retry attempts reached, aborting',
        'Cannot checkout': 'ERROR: Caught error: No such property: branches for class: hudson.scm.NullSCM',
        'Cannot checkout': 'fatal: Remote branch .* not found in upstream origin',
        'Handshake error on public registry': 'Could not GET \'https://plugins-artifacts.gradle.org',
        'Artifactory(TLS handshake)': 'net/http: TLS handshake timeout',
        'cannot open front1_testjsp.log': 'tail: cannot open \'/opt/kyriba/logs/kyriba/front1_testjsp.log\'',
        'Jenkins job executed on deleted branch': 'stderr: fatal: Couldn\'t find remote ref refs/heads',
        'Compilation issue': '<message>Failed to parse',
        'Empty disk space': 'No space left on device',
        'Sonar Quality Gate': 'Pipeline aborted due to quality gate failure',
        'Cannot log to artifactory': 'Error: Cannot perform an interactive login from a non TTY device',
        'AWS CLI Failed': 'ImportError: cannot import name docevents',
        'KeyCloak postgresl init issue': 'LOG:  autovacuum launcher shutting down',
        'Cannot initialize Keycloak': 'Keycloak env failed',
        'Node revoked': 'ChannelClosedException',
        'Node revoked': 'hudson.model.Node',
        'Node revoked': 'RemovedNodeListener',
        'Out of memory': 'java.lang.OutOfMemoryError: Java heap space',
        'Fail to push on artifactory': 'Failed to publish publication',
        'Node revoked': 'Could not connect to i-',
        'Node revoked': 'Remote call on JNLP4-connect connection from ip-',
        'Node revoked': 'Error from server (NotFound): namespaces',
        'Node revoked': 'Required context class hudson.model.Node is missing',
        'Green Mail connection': 'Invalid login/password for user id minsk-qas@kyriba.com',
        'Node revoked': 'ssh_exchange_identification: Connection closed by remote host',
        'Node revoked': 'Cannot contact i-',
        'Node revoked': 'was marked offline: Node is being removed',
        'Issue at KS container creation test': 'Failed in branch Create KS container',
        'Compilation issue': 'Task :Git:compileJava FAILED',
        'Cannot checkout': 'com.cloudbees.jenkins.plugins.bitbucket.api.BitbucketRequestException: HTTP request error.',
        'Cannot checkout': 'error: git-remote-https died of signal 15',
        'Test failure - Missing FKRUN referenced by Firco': 'There is no running FKRUN monitor (exit with code 2)',
        'Test failure - Missing FKRUN referenced by Firco': 'Cannot connect FKRUN monitor (exit with code 1)',
        'Test failure - AWS issue': ': net/http: request canceled while waiting for connection (Client.Timeout exceeded while awaiting headers)',
        'Test failure - D-Bus': 'Failed to get D-Bus connection: No such file or directory',
        'Test failure - Upload logs': 'cp: cannot stat \'/opt/kyriba/logs/kyriba/',
        'Compilation issue': 'does not implement ServerInterface',
        'Test failure - Oracle container start-up': '[ERROR] Oracle-xe can\'t start!',
        'Test failure - Could not pull image': 'error pulling image configuration: received unexpected HTTP status:',
        'Cannot checkout': 'ERROR: Caught error: URL: https://api.bitbucket.org',
        'Node revoked': 'Remote call on i-',
        'Git merge conflicts': 'Automatic merge failed; fix conflicts and then commit the result',
        'API changed detected by JApiCmp': '> Detected binary changes between',
        'Remote job failure (TS Build/Create docker image)': 'The remote job did not succeed',
        'Remote job failure (TS Build/Create docker image)': 'ERROR: Remote build failed with \'ExceedRetryLimitException\'',
        'Cannot checkout': 'Caught error: Could not determine exact tip revision of PR-',
        'Jenkins - Git error': 'No such property: userRemoteConfigs for class: hudson.scm.NullSCM',
        'Test failure - empty scope': 'None of the test reports contained any result',
        'Test failure - unknown': '[test] Logs on error',
        'Compilation issue when building K8s component': 'k8sBuilderJava::exception - script returned exit code',
        'Compilation issue': '> Compilation failed; see the compiler error output for details.',
        'Invalid helm configuration': 'err 0: ,,,,,',
        'Compilation issue': "Execution failed for task ':.*:compile",
        'UI Compilation issue': "Task :.*React.* FAILED",
        'Compilation issue': 'Execution failed for task',
        'API changed detected by JApiCmp': "Execution failed for task ':japicmp",
        'grepUnitTest': 'Assertion(Failed)?Error at',
        'grepUnitTestExecutionFailed': "Execution failed for task ':.*test",
        'Exception found at service startup': r'\bstacktrace\b.*?\bexception\b',
        'Jenkins restart': 'after Jenkins restart',
        'sonarqube failure': 'Task :sonarqube FAILED',
        'Exception found':'java.lang.NullPointerException',
        'Unit test failed': 'java.lang.ArithmeticException: Division by zero'
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
}

def retrieveLogs() {
    return currentBuild.rawBuild.getLog(10000).reverse()
}

def retrieveBuildStatus() {
    return currentBuild.resultIsWorseOrEqualTo('FAILURE') ? 'FAILURE' : 'SUCCESS'
}
