// Declarative pipeline, see https://jenkins.io/doc/book/pipeline/syntax/#declarative-pipeline
pipeline {
    agent any
    triggers { 
        pollSCM('H/30 * * * *') 
    }
    
    tools {
        maven 'maven-3.5.0'
        jdk 'jdk-8'
    }
    
    options {
        timestamps()
        timeout(time: 1, unit: 'HOURS')
        buildDiscarder(logRotator(numToKeepStr:'10'))
    }
    
    // The potential choices, one per line. The value on the first line will be the default.
    parameters {
        booleanParam(name: 'DO_RELEASE', defaultValue: false, description: 'Perform a Release Build?')
    }
    
    stages {
        // 'readMavenPom' requires the 'pipeline-utility-steps' plugin 
        stage ('Checkout') {
            steps {
                script {
                    pom = readMavenPom file: 'pom.xml'
                    projectVersion = pom.version
                    projectVersion = projectVersion.minus("-SNAPSHOT")
                    revCount = sh(script: 'git rev-list --count HEAD', returnStdout: true).trim()
                    projectVersion = projectVersion + "." + revCount

                    currentBuild.displayName = "#${BUILD_NUMBER} - ${projectVersion}"
                }
            }
        }
    
        stage('Build') {
            steps {
                script {
                    if (params.DO_RELEASE) {
                        projectVersion = projectVersion.minus("-SNAPSHOT")
                        currentBuild.displayName = "#${BUILD_NUMBER} v${projectVersion}"
                        // TODO not sure what we want here
                        // add a tag 0.0.1.revCount (=${projectVersion}) once tests are done, ie
                        // 1. run clean compile tests
                        // 2. push tag
                        // 3. run package install using override ${projectVersion}
                        // sh "mvn release:prepare release:clean -DdryRun=true -DupdateDependencies=false -DpreparationGoals=\"clean validate\""
                        // sh "mvn build-helper:parse-version versions:set -DnewVersion=${projectVersion}"
                        // sh "git add --all && git commit -am '[jenkins] prepare release version ${projectVersion}'"
                        // sh "mvn clean deploy -T 3 ${params.MVN_JAVA_8} ${deployToJobRepo} -DforkCount=3 "
                    } else {
                        def exitCode = sh(script: "mvn clean checkstyle:check install deploy -Dselected.spring.profile=${params.PARAM_SPRING_PROFILE} -Dproject.version=${projectVersion}", returnStatus: true)
                        if (exitCode != 0) {
                            currentBuild.result = "UNSTABLE"
                        }
                    }
                	// see https://wiki.jenkins.io/display/JENKINS/Static+Analysis+in+Pipelines
                	// current Warning Plug-In version is 4.68 (see https://www.want.ch/jenkins//pluginManager/installed)
                	// The warnings publisher from the 4.x version does not support CheckStyle yet.
	                // def checkstyleServer = scanForIssues tool: [$class: 'CheckStyle'], pattern: 'funnel-server/target/checkstyle-result.xml'
	                // publishIssues issues:[checkstyleServer]
	                // def lintClient = scanForIssues tool: [$class: 'CheckStyle'], pattern: 'webclient/target/lint-reports/checkstyle-result.xml'
	                // publishIssues issues:[lintClient]
                }
                // pattern: comma- or space-separated list of patterns of files that must be included.
                step([$class: 'CheckStylePublisher', canComputeNew: false, defaultEncoding: '', healthy: '', 
                	unstableTotalHigh: '0', unstableTotalNormal: '100', pattern: 'funnel-server/target/checkstyle-result.xml, webclient/target/lint-reports/checkstyle-result.xml', unHealthy: ''])
            }
        }
    }
    
    post {
        always {
            junit allowEmptyResults: true, testResults: 'extension-api/target/surefire-reports/**/*.xml,extension-util/target/surefire-reports/**/*.xml'
        }
    }
}
