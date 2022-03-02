podTemplate(containers: [
    containerTemplate(name: 'docker', image: 'docker', command: 'cat', ttyEnabled: true),
    containerTemplate(name: 'kubectl', image: 'lachlanevenson/k8s-kubectl:v1.18.3', command: 'cat', ttyEnabled: true),
],
volumes: [
  hostPathVolume(mountPath: '/var/run/docker.sock', hostPath: '/var/run/docker.sock')
]) {
    node(POD_LABEL) {
        def frontrepo = "eypk9673/eagle-web-front"
        def backrepo = "eypk9673/eagle-back"

        stage ('Checkout github branch') {
            mattermostSend (
                color: "#439FE0",
                message: "Build START: ${env.JOB_NAME} #${env.BUILD_NUMBER} (<${env.BUILD_URL}|Link to build>)"
            )
            checkout scm
        }

        stage ('Build and Push docker image') {
            container ('docker') {
                withCredentials([[
                    $class: 'UsernamePasswordMultiBinding',
                    credentialsId: 'dockerhub_creden',
                    usernameVariable: 'DOCKER_HUB_USER',
                    passwordVariable: 'DOCKER_HUB_PASSWORD'
                ]])  {
                    sh ('echo ${DOCKER_HUB_PASSWORD} | docker login -u $DOCKER_HUB_USER --password-stdin')
                    parallel([
                      "Frontend": {
                        dir ('web') {
                            try {
                                sh """
                                    docker build -t ${frontrepo}:${env.BUILD_NUMBER} .
                                    docker push ${frontrepo}:${env.BUILD_NUMBER}
                                """
                            } catch (e) {
                                mattermostSend (
                                    color: "danger", 
                                    message: "Frontend Build Failed: ${env.JOB_NAME} #${env.BUILD_NUMBER} (<${env.BUILD_URL}|Link to build>)"
                                )
                                error "Frontend Build failed"
                            }
                        }
                      },
                      "Backend": {
                        dir ('backend') {
                            try {
                                sh """
                                    docker build -t ${backrepo}:${env.BUILD_NUMBER} .
                                    docker push ${backrepo}:${env.BUILD_NUMBER}
                                """
                            } catch (e) {
                                mattermostSend (
                                    color: "danger", 
                                    message: "Backend Build Failed: ${env.JOB_NAME} #${env.BUILD_NUMBER} (<${env.BUILD_URL}|Link to build>)"
                                )
                                error "Backtend Build failed"
                            }
                        }
                      }
                    ])
                  }
            }
			  }
		    
        stage('Apply kubernetes') {
            container('kubectl') {
                sh """
                     kubectl set image deployment web-front web-front=${frontrepo}:${env.BUILD_NUMBER} -n default
                     kubectl set image deployment eagle-back eagle-back=${backrepo}:${env.BUILD_NUMBER} -n default
                """
            }
        }
        stage('done') {
            mattermostSend (
                color: "good", 
                message: "Build SUCCESS: ${env.JOB_NAME} #${env.BUILD_NUMBER} (<${env.BUILD_URL}|Link to build>)"
            )
        }
    }
}
