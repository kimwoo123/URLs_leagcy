<template>
  <main>
    <q-item clickable v-ripple @click="openDialog">
      <q-item-section avatar>
        <q-icon name="add" />
      </q-item-section>
      <q-item-section>
        폴더 추가하기
      </q-item-section>
    </q-item>

    <q-dialog v-model="isOpen">
        <q-card style="min-width: 350px">
          <form @submit.stop.prevent="createNewFolder" @reset.stop.prevent="closeDialog" >

          <q-card-section>
            <div class="text-h6">폴더 이름을 정해주세요.</div>
          </q-card-section>

          <q-card-section class="q-pt-none">
            <q-input 
              dense 
              v-model="folderName.value" 
              autofocus 
              :rules="[ ruleSameName, ruleMinWords, ruleMaxWords]"
              @keyup.enter="createNewFolder"
            />
            <q-input v-if="false"></q-input>
          </q-card-section>

          <q-card-actions align="right" class="text-primary">
            <q-btn flat label="취소" v-close-popup type="reset"/>
            <q-btn flat label="만들기" type="submit"/>
          </q-card-actions>

          </form>
        </q-card>
    </q-dialog>
  </main>
  
</template>

<script>
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
import { useQuasar } from 'quasar'
import { ref, computed } from 'vue'

export default {
  setup() {
    const $store = useStore()
    const $router = useRouter()
    const $q = useQuasar()

    const folderName = computed({
      get: () => ref('')
    })

    const folderNameList = computed({
      get: () => {
        return $store.state.urls.folders.map( x => x.folder_name)
      }
    })

    const isOpen = ref(false)

    const openDialog = () => {
      isOpen.value = true
    }

    const closeDialog = () => {
      isOpen.value = false
      folderName.value.value = ''
    }


    const createNewFolder = async() => {
      const folder_name = folderName.value.value

      if (folderNameList.value.includes(folder_name)) {
        $q.notify({
          type: 'negative',
          message: '같은 폴더 이름을 사용할 수 없어요!'
        })
      } else if (folder_name.length < 1 || !folder_name) {
        $q.notify({
          type: 'negative',
          message: '폴더 이름을 한 글자 이상 입력해주세요.'
        })
      } else if (folder_name.length > 10) {
        $q.notify({
          type: 'negative',
          message: '폴더 이름을 10자 이하로 입력해주세요.'
        })
      }else {
        const folderData = {
          folder_name : folder_name
        }
        closeDialog()
        await $store.dispatch('urls/CREATE_FOLDER', folderData)
        // console.log('폴더아이디', $store.getters['urls/folderNow.']._id)
        closeDialog()
        // $router.push({ name: 'MyFolder', params:{ folder_id : $store.getters['urls/folderNow._id']}})
      }
    }

    const ruleSameName = (val) => {
      return new Promise((resolve, reject) => {
        setTimeout(() => {
          resolve(!folderNameList.value.includes(val) || '같은 폴더 이름을 사용할 수 없어요.')
        }, 100)
      })
    }

    const ruleMinWords = (val) => {
      return new Promise((resolve, reject) => {
        setTimeout(() => {
          resolve((val && val.length > 0) || '폴더 이름을 한 글자 이상 입력해주세요.')
        }, 100)
      })
    }

    const ruleMaxWords = (val) => {
      return new Promise((resolve, reject) => {
        setTimeout(() => {
          resolve(val.length < 11 || '폴더 이름을 10자 이하로 입력해주세요.')
        }, 100)
      })
    }

    return {
      folderName,
      isOpen,
      openDialog,
      closeDialog,
      createNewFolder,
      prompt: ref(false),
      ruleMinWords,
      ruleSameName,
      ruleMaxWords,
    }
  }

}
</script>

<style>

</style>