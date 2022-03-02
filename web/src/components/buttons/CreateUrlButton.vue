<template>
  <main>
    <q-btn flat round icon="note_add" @click="openDialog" size="13px" />

    <q-dialog v-model="isOpen">
      <q-card style="min-width: 350px">
        <q-card-section>
          <div class="text-h6">추가할 URL을 입력해주세요.</div>
        </q-card-section>
        <q-card-section class="q-pt-none">
          <q-input @blur="recommendTag" v-model="urlName" dense autofocus :rules="[ruleSameUrl]" />
          <q-input v-if="false"></q-input>
        </q-card-section>
        <q-card-section>
          <q-select
            v-model="category"
            :options="categoryOption"
            label="카테고리"
          />
        </q-card-section>
        <q-card-section>
          <div v-if="recommendResult.value[0]" class="tags">
            추천된 태그:
            <span v-for="(tag, index) in recommendResult.value" :key="index" class="tag-add">
              <span @click="addTag(tag)"># {{ tag }}&nbsp;&nbsp; </span>
            </span>
          </div>
          <div v-else></div>
        </q-card-section>
        <q-card-section>
          <div class="text-h6">함께 저장할 태그를 입력해주세요.</div>
        </q-card-section>
        <q-card-section class="q-pt-none">
          <q-input float-label="Floating Label" v-model="customTags" />
          <q-input v-if="false"></q-input>
        </q-card-section>

        <span v-if="splitTags">
          <span v-for="(tagObj, index) in splitTags" :key="index">
            <q-chip v-if="tagObj">{{ tagObj }}</q-chip>
          </span>
        </span>

        <q-card-actions align="right" class="text-primary">
          <q-btn
            flat
            label="취소"
            @click="resetCategory"
            v-close-popup
            type="reset"
          />
          <q-btn flat label="만들기" @click="createUrl" type="submit" />
        </q-card-actions>
      </q-card>
    </q-dialog>
  </main>
</template>

<script>
import { ref, computed, watch } from "vue";
import { useQuasar } from "quasar";
import { useStore } from "vuex";
import { useRoute } from "vue-router";

export default {
  setup() {
    const $q = useQuasar();
    const $store = useStore();
    const $route = useRoute();
    const isOpen = ref(false);
    const urlName = ref("");
    const customTags = ref("");
    const customTagList = ref([]);

    let category = ref("");
    const categoryOption = [
      "프론트",
      "백엔드",
      "모바일",
      "빅데이터/AI",
      "CS",
      "DevOps",
      "Tools(생산성)",
      "기획/디자인"
    ];

    const resetCategory = () => {
      category.value = "";
    };

    // const recommendResult = ref($store.state.recommend.recommendTag)
    const recommendResult = computed({
      get: () => {
        return ref($store.state.recommend.recommendTag);
      }
    });

    const urlList = computed({
      get: () => {
        return $store.state.urls.folderNow.urls.map(x => x.url);
      }
    });

    const splitTags = computed({
      get: () => {
        return new Set(customTags.value.split(/[\s\,\.]/));
      }
    });

    const addTag = (tag) => {
      customTags.value = customTags.value + tag + ', '
    }

    const recommendTag = () => {
      let recommendData = {
        url: urlName.value,
        count: 5,
        folderId: $route.params.folder_id
      };
      $store.dispatch("recommend/RECOMMEND_TAG", recommendData);
    };

    const createUrl = () => {
      splitTags.value.delete("");
      let urlData = {
        url: urlName.value,
        folder_id: $route.params.folder_id,
        tags: Array.from(splitTags.value)
      };
      if (urlList.value.includes(urlName.value)) {
        $q.notify({
          type: "negative",
          message: "이미 등록된 URL 입니다."
        });
      } else if (urlName.value.trim() === "") {
        $q.notify({
          type: "negative",
          message: "URL을 입력해주세요."
        });
      } else if (category.value === "") {
        $q.notify({
          type: "negative",
          message: "카테고리를 선택해주세요."
        });
      } else {
        $store.dispatch("urls/CREATE_URL", urlData);
        $store.dispatch("recommend/DELETE_RECOMMEND_TAG");
        isOpen.value = false;

        const payload = {
          userid: $store.state.user.userid,
          category: category.value
        };
        $store.dispatch("recommend/PUT_USER_CATEGORY", payload);

        const recommendPayload = {
          url: urlName.value,
          categoryName: category.value
        };
        $store.dispatch("recommend/POST_URL_RECOMMEND", recommendPayload);

        urlName.value = "";
        customTags.value = "";
        category.value = "";
      }
    };

    const ruleSameUrl = val => {
      return new Promise((resolve, reject) => {
        setTimeout(() => {
          resolve(!urlList.value.includes(val) || "이미 등록된 URL 입니다.");
        }, 100);
      });
    };

    const openDialog = () => {
      isOpen.value = true;
    };

    watch(isOpen, () => {});

    return {
      resetCategory,
      recommendTag,
      ruleSameUrl,
      openDialog,
      createUrl,
      addTag,
      recommendResult,
      categoryOption,
      customTagList,
      customTags,
      splitTags,
      category,
      urlName,
      isOpen
    };
  }
};
</script>

<style scoped lang="scss">
.tags {
  display: flex;
  flex-wrap: wrap;
  gap: 4px 6px;
  font-size: 15px;

  .tag {
    padding: 1px 8px;
    border: 1px solid $lightgray;
    border-radius: 14px;
    background-color: $verylightgray;
    color: $darkgray;
    cursor: pointer;
  }

  .tag-add {
    cursor: pointer;
    padding: 1px 8px;
    border: 1px solid $lightgray;
    border-radius: 14px;
    color: $darkgray;
    font-size: 12px;
    min-height: 0;
  }

  .q-btn:before {
    box-shadow: 0 0 0 0;
  }
}
</style>