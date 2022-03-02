<template>
  <main>
    <q-card flat bordered class="my-card bg-grey-1" v-if="!isUpdating">
      <q-card-section class="q-pa-none">
        <q-item>
          <q-item-section avatar>
            <q-avatar size="32px">
              <img :src="memoItem.user.avatar" />
            </q-avatar>
          </q-item-section>
          <q-item-section>
            {{ memoItem.user.nickname }}
          </q-item-section>
          <q-item-section side v-if="isAuthor">
            <q-btn color="grey-7" round flat icon="more_vert" size="12px">
              <q-menu cover auto-close>
                <q-list>
                  <q-item clickable @click="deleteMemo">
                    <q-item-section>삭제</q-item-section>
                  </q-item>
                  <q-item clickable @click="toggleUpdating">
                    <q-item-section>수정</q-item-section>
                  </q-item>
                </q-list>
              </q-menu>
            </q-btn>
          </q-item-section>
        </q-item>
      </q-card-section>

      <q-separator />

      <q-card-section v-if="!isUpdating">

        <q-expansion-item
          dense
          dense-toggle
          expand-icon-toggle
          expand-separator
          label="HIGHLIGHT"
          header-class="text-primary q-pa-none q-px-3"
          v-if="memoItem.highlight"
          
        >
          <q-card style="background:transparent;">
            <q-card-section class="q-pa-none transparent" style="padding-bottom: 15px;">
              {{ memoItem.highlight }}
            </q-card-section>
          </q-card>
        </q-expansion-item>

        <div v-html="computedMemoContent" v-else></div>
      </q-card-section>
    </q-card>

    <q-card flat bordered v-if="isUpdating">
      <q-card-section class="q-pa-none">
        <q-item>
          <q-item-section avatar>
            <q-avatar size="32px">
              <img :src="memoItem.user.avatar" />
            </q-avatar>
          </q-item-section>
          <q-item-section>
            {{ memoItem.user.nickname }}
          </q-item-section>
        </q-item>
      </q-card-section>

      <q-card-section class="q-pt-none">
        <q-input borderless autogrow v-model="content" autofocus />
      </q-card-section>

      <q-card-section class="q-pt-none">
        <div class="row q-gutter-xs justify-end">
          <q-btn
            no-caps
            flat
            color="grey"
            label="취소"
            class="col-4"
            @click="cancleUpdating"
          />

          <q-btn
            no-caps
            unelevated
            color="primary"
            label="등록"
            class="col-4"
            @click="updateMemo"
          />
        </div>
      </q-card-section>
    </q-card>
  </main>
</template>

<script>
import { useStore } from "vuex";
import { computed, ref } from "vue";

export default {
  props: ["memoItem"],
  setup(props) {
    const $store = useStore();

    let content = ref(props.memoItem.content);
    const isUpdating = ref(false);

    const computedMemoContent = computed({
      get: () => content.value.toString().replace(/(?:\r\n|\r|\n)/g, "<br />")
    });

    const deleteMemo = () => {
      const memoData = {
        memos_id: $store.state.urls.selectedMemoId,
        memo_id: props.memoItem._id
      };
      $store.dispatch("urls/DELETE_URL_MEMO", memoData);
    };

    const toggleUpdating = () => {
      isUpdating.value = !isUpdating.value;
    };

    const cancleUpdating = () => {
      isUpdating.value = !isUpdating.value;
      content.value = props.memoItem.content;
    };

    const updateMemo = () => {
      const memoData = {
        memos_id: $store.state.urls.selectedMemoId,
        memo_id: props.memoItem._id,
        content: content.value
      };
      $store.dispatch("urls/PUT_URL_MEMO", memoData);
      toggleUpdating();
    };

    const isAuthor = computed({
      get: () => {
        if ($store.state.user.useremail === props.memoItem.user.email) {
          return true;
        } else {
          return false;
        }
      }
    });
    return {
      deleteMemo,
      isAuthor,
      computedMemoContent,
      content,
      updateMemo,
      isUpdating,
      toggleUpdating,
      cancleUpdating
    };
  }
};
</script>

<style></style>
