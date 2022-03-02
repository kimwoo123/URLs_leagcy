<template>
  <div>
    <q-btn flat round icon="person_add_alt_1" @click="isOpen = true" />

    <q-dialog v-model="isOpen">
      <q-card>
          <q-item class="row ">
            <q-item-section class="q-pl-sm">
              팀원 관리
            </q-item-section>

              <q-btn
                flat
                icon="close"
                color="grey"
                v-close-popup
                @click="resetForm"
              />
          </q-item>

        <q-separator />

        <q-card-section v-if="myPermission !== 0">
          <q-form class="row q-gutter-md" @submit="AddUser">
            <q-item>
              <q-item-section>
                <div class="row">
                  <q-input autofocus v-model="email" placeholder="이메일을 적어주세요." type="email" class="q-mr-md"/>
                  <q-select
                    borderless
                    color="primary"
                    v-model="selectedOption"
                    :options="options"
                  />
                </div>
              </q-item-section>
              <q-item-section side>
                <q-btn unelevated rounded label="초대" type="submit" color="primary" class="q-px-lg q-py-xs"/>
              </q-item-section>
            </q-item>
          </q-form>
        </q-card-section>

        <q-card-section>
          <div class="q-gutter-md">
            <q-list>
              <q-item v-ripple v-for="user in userList" :key="user.email">
                <q-item-section avatar>
                  <q-avatar>
                    <img :src="user.avatar" alt="사용자 프로필 사진" />
                  </q-avatar>
                </q-item-section>
                <q-item-section>{{ user.nickname }}</q-item-section>

                  <q-item-section side v-if="myPermission ===2 && user.permission.value === 2" class="q-mr-lg">
                    {{user.permission.label}}
                  </q-item-section>

                  <q-item-section side v-if="myPermission ===2 && user.permission.value !== 2">
                    <folder-user-permission-select :user="user" />
                  </q-item-section>

                <q-item-section side class="q-ml-xl" v-if="myPermission !==2">
                  {{user.permission.label}}
                </q-item-section>

              </q-item>
              <q-card-actions align="right" class="text-primary">
                <q-btn flat v-if="myPermission !==2" label="그룹 탈퇴하기" @click="leaveFolder" type="submit" />
              </q-card-actions>
            </q-list>
          </div>
        </q-card-section>
      </q-card>
    </q-dialog>
  </div>
</template>

<script>
import { ref, computed, watch } from "vue";
import { useQuasar } from "quasar";
import { useStore } from "vuex";
import { useRoute } from "vue-router" 
import folderUserPermissionSelect from "../select/folderUserPermissionSelect.vue";

export default {
  components: { folderUserPermissionSelect },
  props: ["folderData"],
  setup(props) {
    const $store = useStore();
    const $route = useRoute();
    const isOpen = ref(false);

    const myPermission = computed({
      get: () => $store.getters['urls/permissionNow']
    })

    const userList = computed({
      get: () =>
        $store.getters["urls/folderNow"].users.map(x => {
          let label = "읽기";
          if (x.permission === 0) {
            label = "읽기";
          } else if (x.permission === 1) {
            label = "편집";
          } else {
            label = "소유자";
          }
          const user = {
            avatar: x.avatar,
            email: x.email,
            nickname: x.nickname,
            permission: {
              label: label,
              value: x.permission
            }
          };
          return user;
        })
    });

    const email = ref("");
    const selectedOption = ref({ label: "읽기", value: 0 });
    const options = [
      {
        label: "읽기",
        value: 0
      },
      {
        label: "편집",
        value: 1
      }
    ];

    const AddUser = () => {
      const folderUserData = {
        folder_id: props.folderData._id,
        email: email.value,
        permission: selectedOption.value.value
      };
      $store.dispatch("urls/ADD_FOLDER_USER", folderUserData);
    };

    const leaveFolder = () => {
      const folderData = {
          folderId: props.folderData._id,
          userId: $route.params.id
      } 
      $store.dispatch("urls/LEAVE_FOLDER", folderData)
    }

    const resetForm = () => {
      email.value = "";
      selectedOption.value = { label: "읽기", value: 0 };
    };

    watch(isOpen, resetForm);

    return {
      myPermission,
      isOpen,
      userList,
      email,
      selectedOption,
      options,

      AddUser,
      resetForm,
      leaveFolder
    };
  }
};
</script>

<style></style>
