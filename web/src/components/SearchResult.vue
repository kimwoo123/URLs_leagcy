<template>
  <main
    v-if="searchData.length !== 0 || searchPage.length !== 0"
    class="q-py-lg"
  >
    <q-item class="col-12 text-h5">
      검색 결과
    </q-item>

    <div
      class="row q-pa-md items-start q-gutter-lg justify-center"
      v-if="searchData.length !== 0"
    >
      <template
        v-for="urlItem in searchData[0].urls"
        :key="urlItem.memos_id"
        class="col-xs-12 col-sm-6 col-md-4"
      >
        <folder-url-card :urlItem="urlItem" />
      </template>
    </div>

    <div
      class="row q-pa-md items-start q-gutter-lg justify-center"
      v-if="searchPage.length !== 0"
    >
      <template v-for="(urlItem, index) in searchPage" :key="index">
        <div v-if="urlItem._source">
          <folder-url-card :urlItem="urlItem._source" />
        </div>
      </template>
    </div>

    <q-item
      clickable
      class="text-h5 text-grey text-center"
      @click="resetSearch"
    >
      <q-item-section>
        검색 리셋
      </q-item-section>
    </q-item>
  </main>
</template>

<script>
import { computed, watch, onUnmounted } from "vue";
import { useStore } from "vuex";
import FolderUrlCard from "src/components/cards/FolderUrlCard.vue";

export default {
  components: { FolderUrlCard },
  setup() {
    const $store = useStore();

    const searchData = computed({
      get: () => $store.getters["urls/searchData"]
    });

    const searchPage = computed({
      get: () => $store.getters["recommend/searchResult"]
    });

    const resetSearch = () => {
      if (searchPage.value.length !== 0) {
        $store.dispatch("recommend/CHANGE_SEARCH_MODE");
      }
      $store.dispatch("urls/DELETE_URL_SEARCH");
      $store.dispatch("recommend/DELETE_TAG_SEARCH_RESULT");
    };

    return {
      searchData,
      searchPage,
      resetSearch
    };
  }
};
</script>

<style></style>
