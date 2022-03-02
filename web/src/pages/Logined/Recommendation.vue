<template>
  <main class="q-pa-sm">
    <q-item class="q-pb-lg">
      <q-item-section avatar>
        <q-icon name="auto_awesome" size="40px" class="folder-icon" />
      </q-item-section>
      <q-item-section class="text-h6">
        추천 페이지
      </q-item-section>
    </q-item>
    <!-- <q-list> -->
    <div class="row" v-if="loading">
      <div class="row q-gutter-lg justify-center">
        <q-card
          class="my-card col-xl-2 col-lg-3 col-md-4 col-sm-5 col-10"
          flat
          bordered
          v-for="urlItem in recommendUrls"
          :key="urlItem"
        >
          <q-card-section horizontal>
            <q-card-section v-if="urlItem.title === null" class="col-7">
              <a :href="urlItem.url">
                당신이 좋아할 포스트
              </a>
            </q-card-section>
            <q-card-section v-else class="col-7">
              <a :href="urlItem.url">
                {{ urlItem.title }}
              </a>
            </q-card-section>

            <q-img
              v-if="urlItem.og_image === null"
              class="col-5 cursor-pointer"
              src="~assets/none_pic_pink.png"
              :ratio="1"
              @click="clickImg"
            />
            <q-img
              v-else
              class="col-5 cursor-pointer"
              :src="urlItem.og_image"
              :ratio="1"
              @click="clickImg(urlItem, e)"
            />
          </q-card-section>
        </q-card>
      </div>
    </div>

    <div v-else class="row q-gutter-lg justify-center">
      <q-spinner-ios color="primary" size="10em" class="q-pt-xl" />
    </div>
  </main>
</template>

<script>
import { useStore } from "vuex";
import { computed, toRaw } from "vue";
import { openURL } from "quasar";

export default {
  setup() {
    const $store = useStore();

    const recommendCheck = localStorage.getItem('needRecommend')
    if (recommendCheck === null || recommendCheck === 'true') {
      localStorage.setItem('needRecommend', false)
      $store.dispatch("recommend/GET_RECOMMEND_URL", 10);
    }

    const recommendUrls = computed({
      get: () => $store.getters["recommend/recommendUrls"]
    });

    const loading = computed({
      get: () => $store.getters["recommend/recommendLoading"]
    });

    const clickImg = (url, e) => {
      openURL(toRaw(url).url);
    };

    return {
      recommendUrls,
      loading,
      clickImg
    };
  }
};
</script>

<style scoped lang="scss">
// .my-card {
//   width: 100%;
// }
</style>
