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
    
    parameters {
        booleanParam(name: 'PARAM_PERFORM_RELEASE', description: 'Release version?', defaultValue: false)
        string(name: 'PARAM_RELEASE_NEXT_VERSION', description: 'Next development version', defaultValue: "")
    }
    
    
    stages {
    
    	stage ('Checkout HEAD') {
    		steps {
    			checkout( [
                    $class: 'GitSCM',
                    branches: [[name: 'refs/heads/master']],
                    doGenerateSubmoduleConfigurations: false,
                    extensions: [[
                    	$class: 'AuthorInChangelog',
                    	localBranch: 'master'
                    ]],
                    submoduleCfg: [],
                    userRemoteConfigs: [[credentialsId: 'github', url: 'git@github.com:sniederb/funnel-travel-extension-base.git']]
                ] )
    		}
    	}
    
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
                    def exitCode = sh(script: "mvn release:clean checkstyle:check install deploy -Dproject.version=${projectVersion}", returnStatus: true)
                    if (exitCode != 0) {
                        currentBuild.result = "UNSTABLE"
                    }
                }
                // pattern: comma- or space-separated list of patterns of files that must be included.
                step([$class: 'CheckStylePublisher', canComputeNew: false, defaultEncoding: '', healthy: '', 
                	unstableTotalHigh: '0', unstableTotalNormal: '100', pattern: 'extension-api/target/checkstyle-result.xml,extension-util/target/checkstyle-result.xml', unHealthy: ''])
            }
        }
        
        stage('Create release') {
            when {
                expression { 
                    (currentBuild.result == null || currentBuild.result == 'SUCCESS') &&
                    params.PARAM_PERFORM_RELEASE        
                }
            }
            steps {
            	script {
            		def devVersionCommand = ""
            		if (params.PARAM_RELEASE_NEXT_VERSION != "") {
                		devVersionCommand = "-DdevelopmentVersion=${params.PARAM_RELEASE_NEXT_VERSION}"
                		echo "Next development version manually set to ${params.PARAM_RELEASE_NEXT_VERSION}"
                	}
                	sshagent(['github']) {
		            	sh """
		            		mvn --batch-mode release:prepare release:perform ${devVersionCommand}
		            	"""
                    }
            	}
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
