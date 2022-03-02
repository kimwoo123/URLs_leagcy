# DevOps 포팅 메뉼얼

## Jenkin

```
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
```

- 프론트 엔드, 백엔드, 등과 쿠버네티스를 연결하여 사용하였으면, 
- 몽고디비 및 엘라스틱 서치를 서버에서 설치하여 사용. 


## Property

```text
ENV_TYPE=Running Development
ENV_DEV=Development
VUE_APP_API_URL=https=//k5b201.p.ssafy.io/api
VUE_APP_FIREBASE_API_KEY=AIzaSyBl3JirQQUJUC_t_9bCSaZG-q-XFM-fiTY
VUE_APP_FIREBASE_AUTH_DOMAIN=urls-cf099.firebaseapp.com
VUE_APP_FIREBASE_PROJECT_ID=urls-cf099
VUE_APP_FIREBASE_STORAGE_BUCKET=urls-cf099.appspot.com
VUE_APP_FIREBASE_MESSAGEING_SENDER_ID=784803780405
VUE_APP_FIREBASE_APP_ID=1:784803780405:web:a5be70e311825849bd1e7c
VUE_APP_FIREBASE_MEASUREMENT_ID=G-690JDSMZ4F
```
