<template>
  <div class="column q-pa-sm q-gutter-sm">
    <q-item>
      <q-item-section>{{ urlTitle }}</q-item-section>
      <q-item-section side>
        <q-btn flat round color="grey" icon="close" @click="closeDrawer" />
      </q-item-section>
    </q-item>
    <div v-for="memoItem in memos" :key="memoItem._id">
      <url-memo :memoItem="memoItem" />
    </div>
    <new-memo />
  </div>
</template>

<script>
import { computed } from "vue";
import { useStore } from "vuex";
import { useRouter } from "vue-router";
import NewMemo from "./NewMemo.vue";
import UrlMemo from "./UrlMemo.vue";

export default {
  components: { NewMemo, UrlMemo },
  setup() {
    const $store = useStore();
    const $router = useRouter();

    const memos = computed({
      get: () => $store.state.urls.memos
    });

    const urlTitle = computed({
      get: () => $store.state.urls.selectedMemoTitle
    });

    const closeDrawer = () => {
      $store.dispatch("urls/CLOSE_MEMO");
    };

    return {
      memos,
      closeDrawer,
      urlTitle
    };
  }
};
</script>

<style></style>
