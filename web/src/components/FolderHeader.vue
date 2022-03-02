<template>
  <div class="column folder-header-container text-h6">
    <q-item>
      <q-item-section avatar>
        <q-icon
          name="home"
          size="40px"
          class="folder-icon"
          v-if="folderData._id === ''"
        />
        <q-icon
          name="folder_shared"
          size="40px"
          class="folder-icon"
          v-if="folderData._id !== '' && folderData.shared === true"
        />
        <q-icon
          name="folder_open"
          size="40px"
          class="folder-icon"
          v-if="folderData._id !== '' && folderData.shared === false"
        />
      </q-item-section>

      <q-item-section>
        <q-item-label :lines="1" v-if="!isUpdatingFolderName">
          {{ folderName.value }}

          <q-btn flat round v-if="folderData._id !== '' && myPermission === 2">
            <q-icon name="more_vert" />
            <q-menu cover auto-close self="bottom right">
              <q-list>
                <q-item clickable @click="alert = true">
                  <q-item-section>폴더 삭제</q-item-section>
                </q-item>
                <q-item clickable @click="toggleUpdating">
                  <q-item-section>폴더명 변경</q-item-section>
                </q-item>
              </q-list>
            </q-menu>
          </q-btn>
          <q-item-label caption v-if="myPermission === 0">
            Read Only
          </q-item-label>
        </q-item-label>

        <div v-if="isUpdatingFolderName">
          <q-input
            dense
            v-model="folderName.value"
            autofocus
            :rules="[ruleSameName, ruleMinWords, ruleMaxWords]"
            @keyup.enter="updateFolderName"
          >
            <template v-slot:append>
              <q-btn
                flat
                round
                size="12px"
                icon="clear"
                color="negative"
                @click="toggleUpdating"
              />
              <q-btn
                flat
                round
                size="12px"
                icon="done"
                color="positive"
                @click="updateFolderName"
              />
            </template>
          </q-input>
          <!-- </form> -->
        </div>
      </q-item-section>

      <q-item-section side v-if="folderData._id !== ''">
        <folder-user-button :folderData="folderData" round />
      </q-item-section>
      <q-item-section side v-if="myPermission !== 0 && folderData._id !== ''">
        <create-url-button />
      </q-item-section>
    </q-item>
  </div>

  <q-dialog v-model="alert">
    <q-card>
      <q-card-section class="row items-center">
        <q-avatar
          icon="delete"
          color="negative"
          text-color="white"
          size="24px"
        />
        <span class="q-ml-sm">폴더를 삭제하면 되돌릴 수 없습니다.</span>
      </q-card-section>

      <q-card-actions align="right">
        <q-btn flat label="취소" color="grey" v-close-popup />
        <q-btn
          flat
          label="삭제하기"
          color="primary"
          v-close-popup
          @click="deleteFolder"
        />
      </q-card-actions>
    </q-card>
  </q-dialog>
</template>

<script>
import { ref, computed, watch } from "vue";
import { useStore } from "vuex";
import { useRouter } from "vue-router";
import { useQuasar } from "quasar";
import FolderUserButton from "./buttons/folderUserButton.vue";
import CreateUrlButton from "./buttons/CreateUrlButton.vue";

export default {
  components: { FolderUserButton, CreateUrlButton },
  props: ["folderData"],
  setup(props) {
    const $store = useStore();
    const $router = useRouter();
    const $q = useQuasar();

    const myPermission = computed({
      get: () => $store.getters["urls/permissionNow"]
    });

    const originalFolderName = computed({
      get: () => $store.getters["urls/folderNow"].folder_name
    });

    const folderName = computed({
      get: () => {
        return ref($store.getters["urls/folderNow"].folder_name);
      }
    });

    const folderId = computed({
      get: () => $store.getters["urls/folderNow"]._id
    });

    const folderNameList = computed({
      get: () => {
        const arr = $store.state.urls.folders.map(x => x.folder_name);
        if (arr.includes(originalFolderName.value)) {
          const idx = arr.indexOf(originalFolderName.value);
          arr.splice(idx, 1);
          return arr;
        } else {
          return arr;
        }
      }
    });

    const isUpdatingFolderName = ref(false);

    const toggleUpdating = () => {
      folderName.value.value = $store.getters["urls/folderNow"].folder_name;
      isUpdatingFolderName.value = !isUpdatingFolderName.value;
    };

    const alert = ref(false);

    const deleteFolder = () => {
      $router.push({
        name: "AllUrls",
        params: { id: $store.state.user.userid }
      });
      $store.dispatch("urls/DELETE_FOLDER", props.folderData._id);
    };

    const updateFolderName = async () => {
      const folder_name = folderName.value.value;
      if (folderNameList.value.includes(folder_name)) {
        $q.notify({
          type: "negative",
          message: "같은 폴더 이름을 사용할 수 없어요!"
        });
      } else if (folder_name.length < 1 || !folder_name) {
        $q.notify({
          type: "negative",
          message: "폴더 이름을 한 글자 이상 입력해주세요."
        });
      } else if (folder_name.length > 10) {
        $q.notify({
          type: "negative",
          message: "폴더 이름을 10자 이하로 입력해주세요."
        });
      } else {
        if (folder_name !== originalFolderName.value) {
          const updatedFolderData = {
            id: props.folderData._id,
            folder_name: folder_name
          };
          $store.dispatch("urls/PUT_FOLDER_NAME", updatedFolderData);
        }
        toggleUpdating();
      }
    };

    const ruleSameName = val => {
      return new Promise((resolve, reject) => {
        setTimeout(() => {
          resolve(
            !folderNameList.value.includes(val) ||
              "같은 폴더 이름을 사용할 수 없어요."
          );
        }, 100);
      });
    };

    const ruleMinWords = val => {
      return new Promise((resolve, reject) => {
        setTimeout(() => {
          resolve(
            (val && val.length > 0) || "폴더 이름을 한 글자 이상 입력해주세요."
          );
        }, 100);
      });
    };

    const ruleMaxWords = val => {
      return new Promise((resolve, reject) => {
        setTimeout(() => {
          resolve(val.length < 11 || "폴더 이름을 10자 이하로 입력해주세요.");
        }, 100);
      });
    };

    const searchText = ref("");

    const search = () => {
      const urlData = {
        folder_id: $store.state.urls.folderNow._id,
        pattern: searchText.value
      };
      $store.dispatch("urls/GET_FOLDER_URL_SEARCH", urlData);
    };

    watch(folderId, () => {
      searchText.value = "";
    });

    return {
      myPermission,
      isUpdatingFolderName,
      toggleUpdating,
      folderName,
      updateFolderName,
      alert,
      deleteFolder,
      ruleMinWords,
      ruleSameName,
      ruleMaxWords,
      searchText,
      search
    };
  }
};
</script>

<style scoped lang="scss">
// .folder-header-container {
//   display: flex;
//   align-items: center;
//   padding: 15px 50px 0px 50px;
// }
// .folder-icon {
//   margin-right: 10px;
// }
</style>
