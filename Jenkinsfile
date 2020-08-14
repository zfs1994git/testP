//git凭证
def git_auth = "2c385859-8f89-40d9-a902-add08bc5d1c2"
//git的url
def git_url = "http://192.168.150.189:9999/cntytz-project/cntytz-communist-management.git"
node {
    stage('拉取代码-1') {
        checkout([$class: 'GitSCM', branches: [[name: "*/${branch}"]], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: "${git_auth}", url: "${git_url}"]]])    }

    //stage('代码审查-2') {
    //    def scannerHome = tool 'sonarqube-scanner'
    //    withSonarQubeEnv('sonarqube6.7.4') {
    //        sh """
    //        cd ${project_name}
    //        ${scannerHome}/bin/sonar-scanner
    //        """
    //    }
    //}
    stage('编译安装公共模块-3') {
        //sh "mvn -f cntytz-common clean install"
        sh "mvn -Dprofiles.active=prod -Dnamespace.id=b173fa90-d8b1-42c1-bb82-7cafeb2d32fb -Dserver.addr=192.168.150.217:8848 clean package -DskipTests=true"
    }
}