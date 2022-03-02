<template>
  <div>
    <q-input dense v-model="selectTag" placeholder="Tag를 찾아볼까요?">
      <template v-slot:append>
        <q-icon
          v-if="selectTag !== ''"
          name="close"
          @click="selectTag = ''"
          class="cursor-pointer"
        />
        <q-icon name="search" @click="selectTag" class="cursor-pointer" />
      </template>
    </q-input>
  </div>
</template>

<script>
import { useRoute } from "vue-router";
import { ref, watch } from "vue";
import { useStore } from "vuex";

export default {
  setup() {
    const $store = useStore();
    const $route = useRoute();
    const selectTag = ref("");

    watch(selectTag, val => {
      if (val !== "") {
        let queryData = { searchText: val, folderId: $route.params.folder_id };
        $store.dispatch("recommend/SEARCH_TAG", queryData);
      }
    });
    return {
      selectTag
    };
  }
};
</script>
