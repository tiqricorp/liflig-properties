#!/usr/bin/env groovy

// See https://github.com/capralifecycle/jenkins-pipeline-library
@Library('cals') _

buildConfig {
  dockerNode {
    stage('Checkout source') {
      checkout scm
    }

    insideMaven([version: '3-jdk-11-debian']) {
      stage('Build project') {
        sh 'mvn -s $MAVEN_SETTINGS -B verify'
      }

      if (env.BRANCH_NAME != 'master') {
        echo 'Only branch master gets published'
      } else {
        stage('Publish package to Maven repo') {
          withGitConfig {
            withMavenSettings {
              sh 'mvn -s $MAVEN_SETTINGS -B source:jar deploy scm:tag'
            }
          }
        }
      }
    }
  }
}
