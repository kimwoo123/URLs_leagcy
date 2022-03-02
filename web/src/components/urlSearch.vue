<template>
  <div>
    <q-input
      dense
      v-model="searchText"
      placeholder="URL을 찾아볼까요?"
      maxlength="15"
      @keyup.enter="search"
    >
      <template v-slot:append>
        <q-icon
          v-if="searchText !== ''"
          name="close"
          @click="clearSearchData"
          class="cursor-pointer"
        />
        <q-icon name="search" @click="search" class="cursor-pointer" />
      </template>
    </q-input>
  </div>
</template>

<script>
import { ref, computed, watch } from "vue";
import { useStore } from "vuex";

export default {
  setup() {
    const $store = useStore();
    const searchText = ref("");

    const folderId = computed({
      get: () => $store.getters["urls/folderNow"]._id
    });

    const search = () => {
      const urlData = {
        folder_id: $store.state.urls.folderNow._id,
        pattern: searchText.value
      };
      $store.dispatch("urls/GET_FOLDER_URL_SEARCH", urlData);
    };

    const clearSearchData = () => {
      searchText.value = "";
      $store.dispatch("urls/DELETE_URL_SEARCH");
    };

    watch(folderId, () => {
      searchText.value = "";
      $store.dispatch("urls/DELETE_URL_SEARCH");
    });
    return {
      searchText,
      search,
      clearSearchData
    };
  }
};
</script>

<style></style>
