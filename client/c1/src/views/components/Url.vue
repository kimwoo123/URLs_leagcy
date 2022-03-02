<template>
  <div id="option">
    <template v-if="this.getUsername && this.getToken"
      ><el-row>
        <el-form
          ref="form"
          label-width="80px"
          label-position="left"
          size="small"
        >
          <el-form-item label="url">
            <el-input
              v-model="url"
              size="small"
              placeholder="url을 입력해주세요"
            ></el-input>
          </el-form-item>
          <el-form-item label="카테고리">
            <el-select
              v-model="serviceType"
              placeholder="카테고리를 적어주세요"
            >
              <el-option-group
                v-for="group in serviceTypes"
                :key="group.label"
                :label="group.label"
              >
                <el-option
                  v-for="item in group.options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                >
                  <span style="float: left">{{ item.label }}</span>
                </el-option>
              </el-option-group>
            </el-select>
          </el-form-item>
          <el-form-item label="저장 폴더 선택">
            <el-select v-model="total" placeholder="저장할 폴더를 선택해주세요">
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
          @click="inject"
        >
          <span v-if="saved">
            <i class="el-icon-check"></i>
            URL 저장됨
          </span>
          <span v-else-if="net_false">네트워크를 확인해주세요</span>
          <span v-else>URL 저장</span>
        </el-button>
      </el-row>
    </template>
    <template v-else>
      <div class="no-info">
        <i class="el-icon-s-promotion icon"></i>
        <h2>URL 입력은 로그인을 해야 가능합니다.</h2>
        <p>로그인을 진행해주세요</p>
        <router-link to="/">
          <el-button>바로가기</el-button>
        </router-link>
      </div>
    </template>
  </div>
</template>

<script>
import {mapGetters} from 'vuex';
import mainApi from '../../api/mainApi';
import {CATEGORY} from '../../api/list_url';
import {mixin} from '../../utils/mixin';

export default {
  data() {
    return {
      url: null,
      serviceType: null,
      serviceTypes: CATEGORY,
      saved: false,
      net_false: false,
      token: '',
      total: null,
      folders: [],
    };
  },

  computed: {
    ...mapGetters([
      'getToken',
      'getUsername',
      'getBasic',
      'getBasicFolderName',
    ]),
  },

  methods: {
    initComponent() {
      this.total = this.$store.getters.getBasicFolderName;
      this.folders = this.$store.getters.getFolders;
    },
    async inject() {
      const token = await mainApi.getFromStorage('token');
      const folderId = this.folders.filter(
        folder => folder.folder_name === this.total,
      )[0].folder_id;
      const response = await mainApi.inject(token, folderId, {
        url: this.url,
        tags: [this.serviceType],
      });
      if (response.status === 200) {
        this.saved = true;
      } else {
        this.net_false = true;
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
  mounted() {
    this.getServerFolders();
  },
  mixins: [mixin],
};
</script>

<style lang="scss" scoped>
#option {
  position: relative;
  .no-info {
    display: flex;
    justify-content: center;
    vertical-align: middle;
    flex-direction: column;
    text-align: center;
    height: 230px;

    .icon {
      font-size: 30px;
    }
  }
}
</style>
