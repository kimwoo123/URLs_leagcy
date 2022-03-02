<template>
  <div id="option">
    <template v-if="this.getToken && this.getUsername">
      <div class="duration">
        <p>
          현재 로그인 중입니다.
          <el-button
            type="danger"
            icon="el-icon-close"
            circle
            @click="deleteToken"
          ></el-button>
        </p>
        <p></p>
      </div>

      <aside class="profile-card">
        <header>
          <img v-bind:src="getPhotoUrl" class="hoverZoomLink" />
          <h1>{{ getUsername }}</h1>
          <h2>
            {{ getEmail }}
          </h2>
        </header>
      </aside>
      <section>
        <el-row>
          <el-form
            ref="form"
            label-width="80px"
            label-position="left"
            size="small"
          >
            <el-form-item label="기본 폴더" @click="saveChange">
              <el-select v-model="total" placeholder="기본 폴더를 선택해주세요">
                <el-option-group label="나의 폴더들">
                  <el-option
                    v-for="(folder, index) in folders"
                    :key="index"
                    :label="folder.folder_name"
                    :value="folder.folder_name"
                  >
                    <span style="float: left">{{ folder.folder_name }}</span>
                  </el-option>
                </el-option-group>
              </el-select>
            </el-form-item>
          </el-form>
          <el-button
            :type="saved ? 'success' : 'primary'"
            size="small"
            @click="saveBasic"
          >
            <span v-if="saved">
              <i class="el-icon-check"></i>
              기본 폴더 저장됨
            </span>
            <span v-else-if="net_false">네트워크를 확인해주세요</span>
            <span v-else>기본 폴더 저장</span>
          </el-button>
        </el-row>
      </section>
    </template>
    <template v-else
      ><div><h2>현재 토큰이 없습니다.</h2></div>
      <div>
        <p>{{ this.getToken }}</p>
        <p>{{ this.getUsername }}</p>

        <el-card :body-style="{padding: '0px'}">
          <img
            src="/icons/btn_google_signin_dark_disabled_web@2x.png"
            class="image"
            @click="signIn"
          />
        </el-card></div
    ></template>
  </div>
</template>

<script>
import {getAuth, GoogleAuthProvider, signInWithPopup} from 'firebase/auth';
import {mapGetters} from 'vuex';
import mainApi from '../../api/mainApi';
import {mixin} from '../../utils/mixin';

export default {
  data() {
    return {
      username: null,
      token: null,
      saved: false,
      total: '',
      net_false: false,
      folders: [],
      name: '',
    };
  },

  computed: {
    ...mapGetters([
      'getUsername',
      'getToken',
      'getEmail',
      'getPhotoUrl',
      'getBasic',
      'getBasicFolderName',
      'getFolders',
    ]),
  },

  methods: {
    initComponent() {
      this.username = this.$store.getters.getUsername;
      this.token = this.$store.getters.getToken;
      this.email = this.$store.getters.getEmail;
      this.photoURL = this.$store.getters.getPhotoUrl;
      this.total = this.$store.getters.getBasicFolderName;
      this.folders = this.$store.getters.getFolders;
    },
    saveChange() {
      this.save = false;
    },

    deleteToken() {
      this.save(
        {
          username: null,
          email: null,
          token: null,
        },
        'updated',
      );
    },
    async signIn() {
      const provider = new GoogleAuthProvider();
      const auth = getAuth();

      await signInWithPopup(auth, provider)
        .then(result => {
          const credential = GoogleAuthProvider.credentialFromResult(result);
          const token = credential.idToken;
          const {email, displayName, photoURL} = result.user;
          this.username = displayName;
          this.token = token;
          this.photoURL = photoURL;
          this.email = email;
          console.log(email);
          console.log(photoURL);
          console.log(token);
          this.save(
            {
              username: displayName,
              email,
              token,
              photoURL,
            },
            'updated',
          );
        })
        .catch(error => {
          const errorCode = error.code;
          const {errorMessage} = error;
          const {email} = error;
          const credential = GoogleAuthProvider.credentialFromError(error);
        });

      const response = await mainApi.signIn(this.token, {
        email: this.email,
        avatar: this.photoURL,
        nickname: this.username,
      });
      if (response && response.status === 201) {
        console.log('서버 연결 완료');
        await this.getServerFolders();
      } else {
        console.log('서버 연결 실패');
      }
    },
    saveBasic() {
      const payload = {
        basic: this.folders.filter(
          folder => folder.folder_name === this.total,
        )[0].folder_id,
        basic_folder_name: this.total,
      };
      this.save(payload, 'updated');
      console.log(payload);
      console.log(`이것이 기본 폴더 입니다 ${this.getBasic}`);
      this.saved = true;
    },
    async getServerFolders() {
      const response = await mainApi.getFolders(this.token);
      if (response && response.status === 200) {
        console.log('폴더를 불러왔습니다.');
        this.folders = response.data;
        this.save({
          folders: response.data,
        });
      } else {
        console.log('폴더를 불러오지 못했습니다.');
      }
    },
  },
  mixins: [mixin],

  mounted() {
    this.getServerFolders();
  },
};
</script>

<style lang="scss" scoped>
#option {
  position: relative;

  .username {
    font-size: 10px;

    .username-highlighter {
      font-size: 14px;
    }
  }

  .duration {
    position: absolute;
    right: 0;
    color: #909399;
    font-size: 1rem;
  }
  .time {
    font-size: 13px;
    color: #999;
  }

  .bottom {
    margin-top: 13px;
    line-height: 12px;
  }

  .button {
    padding: 0;
    float: right;
  }

  .image {
    width: 100%;
    display: block;
  }

  .clearfix:before,
  .clearfix:after {
    display: table;
    content: '';
  }

  .clearfix:after {
    clear: both;
  }
}
</style>
