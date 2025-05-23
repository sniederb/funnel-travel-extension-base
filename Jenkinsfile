// Declarative pipeline, see https://jenkins.io/doc/book/pipeline/syntax/#declarative-pipeline
pipeline {
    agent any
    triggers { 
        pollSCM('H/30 * * * *') 
    }
    
    tools {
        maven 'maven-3.8.7'
        jdk 'jdk-17'
    }
    
    options {
        timestamps()
        timeout(time: 1, unit: 'HOURS')
        buildDiscarder(logRotator(numToKeepStr:'10'))
        disableConcurrentBuilds()
    }
    
    parameters {
        booleanParam(name: 'PARAM_PERFORM_RELEASE', description: 'Release version?', defaultValue: false)
        string(name: 'PARAM_RELEASE_NEXT_VERSION', description: 'Optionally set next development version, e.g. 1.0.3-SNAPSHOT', defaultValue: "")
    }
    
    
    stages {
    	stage ('Checkout HEAD') {
    		steps {
    			checkout( [
                    $class: 'GitSCM',
                    branches: [[name: '*/master']],
                    doGenerateSubmoduleConfigurations: false,
                    extensions: [[
                    	$class: 'AuthorInChangelog',
                    	$class: 'LocalBranch',
                    	localBranch: "**"
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
                    def exitCode = sh(script: "mvn clean checkstyle:check install -Dproject.version=${projectVersion}", returnStatus: true)
                    if (exitCode != 0) {
                        currentBuild.result = "UNSTABLE"
                    }
                }
                echo "Collecting JUnit test results" 	
                junit allowEmptyResults: true, testResults: 'extension-api/target/surefire-reports/**/*.xml,extension-util/target/surefire-reports/**/*.xml'
                // pattern: comma- or space-separated list of patterns of files that must be included.
                // recordIssues enabledForFailure: true, aggregatingResults: true, tool: checkStyle(pattern: 'extension-api/target/checkstyle-result.xml,extension-util/target/checkstyle-result.xml')
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
                // note that release:perform runs a "mvn deploy site-deploy", which will itself trigger maven central deployment
            	script {
            		def devVersionCommand = ""
            		if (params.PARAM_RELEASE_NEXT_VERSION != "") {
                		devVersionCommand = "-DdevelopmentVersion=${params.PARAM_RELEASE_NEXT_VERSION}"
                		echo "Next development version manually set to ${params.PARAM_RELEASE_NEXT_VERSION}"
                	}
                	sshagent(['github']) {
		            	sh """
		            		mvn --batch-mode release:clean release:prepare release:perform ${devVersionCommand}
		            	"""
                    }
            	}
            }
        }
    }
    
    post {
    	always {
            junit allowEmptyResults: true, testResults: 'target/surefire-reports/**/*.xml'
        }
    }
}
