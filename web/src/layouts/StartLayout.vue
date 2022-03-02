<template>
  <q-layout view="hHh lpR fFf">
    <q-header bordered class="bg-white text-black" id="navbar">
    <q-toolbar style="padding: 0 35px 0 44px;">
      <q-toolbar-title>
        <img src="https://i.imgur.com/2Ojcuu9.png" alt="" class="navbar-logo">
      </q-toolbar-title>
      <google-login-button />
    </q-toolbar>
    </q-header>
    <div>
      <router-view />
    </div>

    <div id="footer">
      <p>Copyright © Team. EAGLE_EYE All Rights Reserved.</p>
      <p>김재현 김문희 김우석 이다은 이두호</p>
      <p class="icon">
        <q-btn flat round padding="3px" icon="img:https://img.icons8.com/material-rounded/24/000000/github.png" />
        <q-btn flat round padding="3px" icon="img:https://img.icons8.com/material-rounded/50/000000/instagram-new.png" />
        <q-btn flat round padding="3px" icon="img:https://img.icons8.com/ios-glyphs/24/000000/twitter--v1.png" />
      </p>
    </div>
  </q-layout>
</template>

<script>
import GoogleLoginButton from '../components/buttons/GoogleLoginButton.vue'

export default {
  components: { GoogleLoginButton },
  setup() {
    function scrollF() {
      if (document.body.scrollTop > 50 || document.documentElement.scrollTop > 50) {
        document.getElementById("navbar").style.top = "0"
      } else {
        document.getElementById("navbar").style.top = "-50px"
      }
    }
    window.addEventListener("scroll", scrollF);
  }
}
</script>

<style scoped lang="scss">
body {
  font-family: -apple-system, BlinkMacSystemFont, 'Apple SD Gothic Neo', Pretendard, Roboto, 'Noto Sans KR', 'Segoe UI', 'Malgun Gothic', sans-serif;
}
#navbar {
  position: fixed;
  top: -50px;
  transition: top 0.5s;
}
.navbar-logo {
  height: 16px;
  widows: 100%;
}
#footer {
  width: 100%;
  height: 130px;
  border-top: 1px solid $lightgray;
  color: $dark;
  padding-top: 35px;
  margin: 0;
  p {
    margin: 0 auto;
    text-align: center;
    font-size: 12px;
  }
  button {
    margin-top: 5px;
    margin-right: 3px;
  } 
}
</style>