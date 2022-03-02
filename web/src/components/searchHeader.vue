<template>
  <div class="row justify-between q-px-md q-py-md">
    <div class="col-xl-1 col-lg-2 col-sm-3 col-12">
      <div v-if="isModURL" class="row items-center">
        <q-btn color="grey" unelevated label="URL" no-caps class="col-6" />
        <q-btn
          color="grey"
          flat
          label="Tags"
          no-caps
          class="col-6"
          @click="changeMode"
        />
      </div>

      <div v-if="!isModURL" class="row items-cente">
        <q-btn
          color="grey"
          flat
          label="URL"
          no-caps
          class="col-6"
          @click="changeMode"
        />
        <q-btn color="grey" unelevated label="Tags" class="col-6" no-caps />
      </div>
    </div>

    <url-search v-if="isModURL" class="col-sm-6 col-12 q-pt-sm" />
    <auto-complete v-if="!isModURL" class="col-sm-6 col-12 q-pt-sm" />
  </div>
</template>

<script>
import { useStore } from "vuex";
import AutoComplete from "./AutoComplete.vue";
import urlSearch from "./urlSearch.vue";
import { ref, watch, computed } from "vue";

export default {
  components: { urlSearch, AutoComplete },
  setup() {
    const $store = useStore();
    const isModURL = computed({
      get: () => $store.getters["recommend/searchMode"]
    });
    const changeMode = () => {
      $store.dispatch("recommend/CHANGE_SEARCH_MODE");
      $store.dispatch("urls/DELETE_URL_SEARCH");
      $store.dispatch("recommend/DELETE_TAG_SEARCH_RESULT");
    };

    return {
      isModURL,
      changeMode
    };
  }
};
</script>

<style></style>
