@Library('pipelines') _
buildLibPipeline([
        buildSnapshotScript: './gradlew clean build publish',
        buildRCScript: './gradlew clean release -Prelease.useAutomaticVersion=true -Prelease.preCommitText=\"[skip ci]\"',
        projectName: 'lib-azimo-logging',
        gitlabProjectNamespace:'tukan/lib-azimo-logging',
        dockerBuildImageType: 'java11',
        artifactGroup: 'com.azimo.tukan',
        serviceName: 'lib-azimo-logging',
        junitReports: 'build/test-results/**/*.xml',
        jacocoReports: false,
        projectEmoji: ':money_with_wings:',
        testTags: 'tukan'
])
