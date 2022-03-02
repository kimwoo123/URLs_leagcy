<template>
  <div>
    <div class="q-pa-xs column q-gutter-xs">
      <q-input
        outlined
        v-model="content"
        type="textarea"
      />
      <div class="row">
        <q-btn 
         no-caps unelevated 
         color="primary"
         label="작성" 
         class="col-6 offset-6"
         :disable="!canUse"
         @click="submitMemo"/>
      </div>
    </div>
  </div>
</template>

<script>
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
import { ref, computed } from 'vue'

export default {
  setup() {
    const $store = useStore()
    const $router = useRouter()

    const memosId = computed({
      get: () => $store.state.urls.selectedMemoId
    })
    let content = ref('')

    const submitMemo = () => {
      const memoData = {
        memos_id: memosId.value,
        content: content.value
      }
      $store.dispatch('urls/CREATE_URL_MEMO', memoData)
      content.value = ''
    }

    let canUse = computed({
      get: () => {
        if (content.value !== '') {
          return true
        } else {
          return false
        }
      }
    })

    return {
      content,
      submitMemo,
      canUse
    }
  }

}
</script>

<style>

</style>