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
                    def exitCode = sh(script: "mvn clean checkstyle:check install deploy -Dproject.version=${projectVersion}", returnStatus: true)
                    if (exitCode != 0) {
                        currentBuild.result = "UNSTABLE"
                    }
                }
                // pattern: comma- or space-separated list of patterns of files that must be included.
                step([$class: 'CheckStylePublisher', canComputeNew: false, defaultEncoding: '', healthy: '', 
                	unstableTotalHigh: '0', unstableTotalNormal: '100', pattern: 'extension-api/target/checkstyle-result.xml,extension-util/target/checkstyle-result.xml', unHealthy: ''])
            }
        }
    }
    
    post {
        always {
            junit allowEmptyResults: true, testResults: 'extension-api/target/surefire-reports/**/*.xml,extension-util/target/surefire-reports/**/*.xml'
        }
        unstable {
            slackSend (color: 'warning', message: "funnel-extension-base: There were test failures in job '${currentBuild.displayName}' (<${env.BUILD_URL}|Open>)")
        }
        failure {
            slackSend (color: 'danger', message: "funnel-extension-base: There were build errors in job '${currentBuild.displayName}' (<${env.BUILD_URL}|Open>)")
        }
    }
}
