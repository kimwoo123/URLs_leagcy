<template>
  <q-scroll-area class="fit">
    <memo-list v-if="isUrlMemoOpen"/>
  </q-scroll-area>
</template>

<script>
import MemoList from '../memos/MemoList.vue'
import { computed } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

export default {
  components: { MemoList },
  setup() {
    const $store = useStore()
    const $router = useRouter()

    const isUrlMemoOpen = computed({
      get: () => $store.getters['urls/isUrlMemoOpen']
    })
    
    return {
      isUrlMemoOpen
    }
  }

}
</script>

<style>

</style>