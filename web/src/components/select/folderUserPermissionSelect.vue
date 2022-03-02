<template>
  <main>
    <q-select
      borderless
      color="primary" 
      v-model="selectedOption" 
      :options="options"
    />

  </main>
</template>

<script>
import { ref, watch } from 'vue'
import { useStore } from 'vuex'

export default {
  props: ['user'],
  setup(props) {
    const $store = useStore()
    const selectedOption = ref({label: props.user.permission.label, value: props.user.permission.value})
    const options = [
      {
        label: '읽기',
        value: 0,
      },
      {
        label: '편집',
        value: 1
      },
      {
        label: '삭제',
        value: -1
      }
    ]

    watch(selectedOption, () => {
      const folderUserData = {
        folder_id: $store.getters['urls/folderNow']._id,
        email: props.user.email,
        permission: selectedOption.value.value
      }
      if (selectedOption.value.value === -1 ) {
        $store.dispatch('urls/DELETE_FOLDER_USER', folderUserData)
      } else {
        $store.dispatch('urls/PUT_FOLDER_USER', folderUserData)
      }
    })

    return {
      selectedOption,
      options
    }
  }
}
</script>

<style>

</style>