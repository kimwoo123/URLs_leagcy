<template>
  <main>
    <div class="row justify-center q-mt-lg">
      <div class="col-sm-12 col-md-10 col-lg-8 col-xl-6">
        <q-item>
          <!-- Avatar -->
          <q-item-section avatar>
            <q-avatar rounded size="100px">
              <img :src="userInfo.avatar" alt="avatar" />
            </q-avatar>
          </q-item-section>

          <!-- Email & Nickname -->
          <q-item-section>
            <q-item-label lines="1" class="text-body1">{{
              userInfo.email
            }}</q-item-label>
            <q-item-label>
              <q-input
                v-if="!editMode"
                v-model="nickname"
                :default="userInfo.nickname"
                label="Nickname"
              />
              <q-item-label v-else class="text-body1 q-py-md">{{
                userInfo.nickname
              }}</q-item-label>
            </q-item-label>
          </q-item-section>
        </q-item>

        <!-- User Catetories -->
        <q-list class="q-mt-md">
          <q-item
            class="row"
            v-for="(idx, item) in getUserCategories.data"
            :key="item"
          >
            <q-item-section class="col-2">
              <q-item-label lines="1">{{ item }}</q-item-label>
            </q-item-section>

            <q-item-section class="col-10">
              <q-linear-progress
                :value="
                  (getUserCategories.data[item] / getUserCategories.cnt) * 3
                "
                :color="
                  colorRange[Object.keys(getUserCategories.data).indexOf(item)]
                "
                class="q-mt-sm"
              />
            </q-item-section>
          </q-item>
        </q-list>

        <!-- Buttons -->
        <q-item class="row justify-center q-mt-md">
          <div v-if="editMode" class="q-mx-xs">
            <q-btn
              flat
              @click="changeDeleteMode"
              color="grey"
              label="회원 탈퇴"
            />
          </div>

          <!-- <div v-if="editMode" class="q-mx-xs">
            <q-btn
              @click="changeEditMode"
              padding="5px 30px"
              color="warning"
              label="수정"
            />
          </div> -->
          <!-- <div v-else class="q-mx-xs">
            <q-btn
              @click="updateUserInfo"
              padding="5px 30px"
              color="warning"
              label="완료"
            />
          </div> -->

          <logout-button v-if="editMode" class="q-mx-xs" />
        </q-item>

        <!-- Alert : User Delete -->
        <q-dialog v-model="deleteMode" persistent>
          <q-card>
            <q-card-section class="row items-center">
              <span class="q-mx-md q-mt-md"
                >정말로 회원 탈퇴를 진행하시겠습니까?</span
              >
            </q-card-section>

            <q-card-actions align="right">
              <q-btn
                flat
                @click="deleteUser"
                label="회원 탈퇴"
                color="grey"
                v-close-popup
              />
              <q-btn flat label="취소" color="primary" v-close-popup />
            </q-card-actions>
          </q-card>
        </q-dialog>
      </div>
    </div>
  </main>
</template>

<script>
import { useStore } from "vuex";
import { useRouter } from 'vue-router'
import { ref, computed, onMounted } from "vue";
import LogoutButton from "components/buttons/LogoutButton.vue";

export default {
  components: { LogoutButton },
  setup() {
    const $store = useStore();
    const $router = useRouter()

    let nickname = ref($store.state.user.username);
    let avatar = ref($store.state.user.avatar);

    let editMode = ref("false");
    let deleteMode = ref(false);

    const userInfo = computed({
      get: () => {
        return {
          email: $store.state.user.useremail,
          nickname: $store.state.user.username,
          avatar: $store.state.user.avatar
        };
      }
    });

    const changeEditMode = () => {
      editMode.value = !editMode.value;
    };

    const changeDeleteMode = () => {
      deleteMode.value = !deleteMode.value;
    };

    const updateUserInfo = () => {
      const loginData = {
        email: $store.state.user.useremail,
        avatar: avatar.value,
        nickname: nickname.value
      };
      $store.dispatch("user/UPDATE_USER_INFO", loginData);
      editMode.value = !editMode.value;
    };

    onMounted(() => {
      $store.dispatch("user/GET_USER_INFO", $store.state.user.userid);
    });

    const getUserCategories = computed({
      get: () => {
        let data = $store.state.user.categories;
        if (typeof data === typeof String) {
          data = JSON.parse(data);
        }

        const cnt = Object.keys(data).reduce((stack, key) => {
          return stack + data[key];
        }, 0);

        return { cnt: cnt, data: data };
      }
    });

    const deleteUser = async () => {
      await $store.dispatch("user/DELETE_USER", $store.state.user.userid);
      await $store.dispatch("user/LOGOUT")
      $router.go()
    };

    const colorRange = [
      "primary",
      "secondary",
      "accent",
      "dark",
      "positive",
      "negative",
      "info",
      "warning"
    ];

    return {
      nickname,
      editMode,
      deleteMode,
      userInfo,
      changeEditMode,
      changeDeleteMode,
      updateUserInfo,
      getUserCategories,
      deleteUser,
      colorRange
    };
  }
};
</script>

<style></style>
