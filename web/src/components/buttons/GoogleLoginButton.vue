<template>
  <q-btn 
    outline 
    rounded 
    color="primary" 
    icon="fab fa-google" 
    label="3초만에 시작하기"
    id="firebaseui-auth-container"
    @click="googleLogin"
  />
</template>

<script>
import { GoogleAuthProvider, getAuth, signInWithPopup } from 'firebase/auth';
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

export default {
  setup() {
    const $store = useStore()
    const $router = useRouter()

    const googleLogin = async() => {
      const provider = new GoogleAuthProvider()
      const gooleAuth = getAuth()

      signInWithPopup(gooleAuth, provider)
        .then(async(result) => {
          const credential = GoogleAuthProvider.credentialFromResult(result);
          const token = credential.idToken;
          const useremail = result.user.email;
          const nickname = result.user.displayName;
          const photoURL = result.user.photoURL;
          const userData = {
            'token' : token,
            'useremail': useremail,
            'nickname': nickname,
            'avatar': photoURL,
          }
          await $store.dispatch('user/LOGIN', userData);
          const userid = $store.state.user.userid
          await $router.push({ name: 'AllUrls', params: { id: userid }})
        }).catch((error) => {
          const errorCode = error.code;
          const errorMessage = error.errorMessage;
          const email = error.email;
          const credential = GoogleAuthProvider.credentialFromError(error);
        })
    }

    return {
      googleLogin
    }
  }
}

</script>

<style>

</style>