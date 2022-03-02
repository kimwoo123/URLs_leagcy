<template>
  <q-btn 
    color="primary" 
    label="로그아웃"
    @click="logout"
  />
</template>

<script>
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

export default {
  setup() {
    const $store = useStore()
    const $router = useRouter()
    
    const logout = async() => {
      await $store.dispatch('user/LOGOUT');
      await $router.push('/')
    }

    return {
      logout
    }
  }
}
</script>

<style>

</style>