<template>
  <main class="q-pa-sm">
    <folder-header :folderData="folderData" v-if="folderId" />
    <search-header />
    <search-result />

    <div
      class="row q-pa-md items-start q-gutter-lg justify-center"
      v-if="searchData.length === 0 && searchPage.length === 0"
    >
      <template v-if="folderData.urls == false">
        <div class="column">
          <img src="https://i.imgur.com/vi3Oloq.png" alt="" style="width: 250px; margin: 11vh 0 2vh;">
          <div class="text-h5 text-grey text-center">폴더가 비었어요!</div>
        </div>
      </template>
      <template
        v-for="urlItem in folderData.urls"
        :key="urlItem.memos_id"
        class="col-xs-12 col-sm-6 col-md-4"
      >
        <folder-url-card :urlItem="urlItem" />
      </template>
    </div>
  </main>
</template>

<script>
import { computed, watch, onUnmounted } from "vue";
import { useStore } from "vuex";
import { useRoute, onBeforeRouteLeave } from "vue-router";
import FolderUrlCard from "src/components/cards/FolderUrlCard.vue";
import FolderHeader from "src/components/FolderHeader.vue";

import SearchHeader from "src/components/searchHeader.vue";
import SearchResult from "src/components/SearchResult.vue";

export default {
  components: { FolderUrlCard, FolderHeader, SearchHeader, SearchResult },

  setup() {
    const $route = useRoute();
    const $store = useStore();

    const searchData = computed({
      get: () => $store.getters["urls/searchData"]
    });

    const searchPage = computed({
      get: () => $store.getters["recommend/searchResult"]
    });

    const folderId = computed({
      get: () => {
        $store.dispatch("urls/GET_FOLDER_ULR", $route.params.folder_id);
        $store.dispatch("urls/CLOSE_MEMO");
        return $route.params.folder_id;
      }
    });

    const folderData = computed({
      get: () => $store.getters["urls/folderNow"]
    });

    watch(folderId, () => {
      const deleteList = $store.getters["urls/willDeleteURL"];
      deleteList.forEach(element => {
        $store.dispatch("urls/DELETE_URL", element);
      });
      $store.dispatch("urls/DELETE_URL_SEARCH");
      $store.dispatch("recommend/DELETE_TAG_SEARCH_RESULT");
    });

    return {
      folderId,
      folderData,
      searchData,
      searchPage
    };
  }
};
</script>

<style></style>
