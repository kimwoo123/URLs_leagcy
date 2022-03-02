<template>
  <q-list padding>
      <router-link :to="{ name: 'AllUrls'}">
        <q-item clickable v-ripple>
          <q-item-section avatar>
            <q-icon name="home" />
          </q-item-section>
          <q-item-section>
            모든 Urls
          </q-item-section>
        </q-item>
      </router-link>

      <q-separator/>
      <router-link :to="{ name: 'Recommendation'}">
        <q-item clickable v-ripple>
          <q-item-section avatar>
            <q-icon name="auto_awesome" />
          </q-item-section>
          <q-item-section>
            추천 페이지
          </q-item-section>
        </q-item>
      </router-link>


      <q-separator/>

      <q-item>
        <q-item-section class="text-grey">개인폴더</q-item-section>
      </q-item>              

      <template v-for="folderItem in folders" :key="folderItem.folder_id">
        <router-link :to="{ name: 'MyFolder', params: { folder_id: folderItem.folder_id }}">
          <q-item clickable v-ripple v-if="!folderItem.shared">
            <q-item-section avatar>
              <q-icon name="folder_open" />
            </q-item-section>
            <q-item-section>
              {{ folderItem.folder_name}}
            </q-item-section>
          </q-item>
        </router-link>
        
      </template>

      <q-separator/>

      <q-item>
        <q-item-section class="text-grey">공용 폴더</q-item-section>
      </q-item>

      <template v-for="folderItem in folders" :key="folderItem.folder_id">
        <router-link :to="{ name: 'OurFolder', params: { folder_id: folderItem.folder_id }}">
          <q-item clickable v-ripple v-if="folderItem.shared">
            <q-item-section avatar>
              <q-icon name="folder_shared" />
            </q-item-section>
            <q-item-section>
              {{ folderItem.folder_name}}
            </q-item-section>
          </q-item>
        </router-link>
      </template>

      <q-separator/>

      <create-new-folder/>


  </q-list>


  
</template>

<script>
import { computed } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
import CreateNewFolder from '../buttons/CreateNewFolder.vue'

export default {
  components: { CreateNewFolder },
  setup() {
    const $store = useStore()
    const $router = useRouter()

    $store.dispatch('urls/GET_FOLDER')
    const folders = computed({
      get: () => $store.state.urls.folders
     })

    return {
      folders,
    }
  }

}
</script>

<style>

</style>