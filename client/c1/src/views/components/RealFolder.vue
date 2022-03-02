<template>
  <div id="releases">
    <template v-if="this.getUsername && this.getToken">
      <template v-if="move">
        <el-row>
          <el-timeline>
            <el-timeline-item
              v-for="(folder, index) in folders"
              :key="index"
              placement="top"
            >
              <el-card :body-style="{padding: '10px'}">
                <span slot="header">
                  <el-button type="text">
                    <h3>{{ index }}. {{ folder.folder_name }}</h3>
                  </el-button>
                </span>
                <div>
                  <el-button
                    type="text"
                    @click="change(folder.folder_id, folder.folder_name)"
                  >
                    이동하기
                  </el-button>
                </div>
              </el-card>
            </el-timeline-item>
          </el-timeline>
        </el-row>
      </template>
      <template v-else>
        <h3>{{ this.folders_name }} - {{ this.folders_id }}</h3>
        <el-row>
          <el-timeline>
            <el-timeline-item
              v-for="(url, index) in urls"
              :key="index"
              placement="top"
            >
              <el-card :body-style="{padding: '10px'}">
                <span slot="header">
                  <a :href="url.url" target="_blank">
                    <el-button type="text" style="margin-left: 10px">
                      <h3>{{ index + 1 }}. {{ url.url }}</h3>
                    </el-button>
                  </a>
                </span>
                <a href="https://k5b201.p.ssafy.io/" target="_blank">
                  <el-button> 관리 페이지 이동하기</el-button>
                </a>
              </el-card>
            </el-timeline-item>
          </el-timeline>
        </el-row></template
      ></template
    ><template v-else>
      <div class="no-info">
        <i class="el-icon-s-promotion icon"></i>
        <h2>나의 폴더는 로그인을 해야 가능합니다.</h2>
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
import {mixin} from '../../utils/mixin';

export default {
  data() {
    return {
      folders: [],
      folders_id: '',
      folders_name: '',
      loading: false,
      move: true,
      urls: [],
    };
  },
  computed: {
    ...mapGetters(['getToken', 'getUsername']),
  },

  methods: {
    initComponent() {
      this.folders = this.$store.getters.getFolders;
    },
    async change(id, name) {
      this.folders_id = id;
      this.folders_name = name;
      this.move = !this.move;

      const token = await mainApi.getFromStorage('token');
      const response = await mainApi.getUrls(token, id);
      if (response && response.data) {
        if (response.status === 200) {
          console.log('url 확인하기');
          console.log(response.data);
          this.urls = response.data.urls;
        }
      }
    },
  },

  mixins: [mixin],
};
</script>

<style lang="scss" scoped>
#releases {
  min-height: 170px;

  .el-timeline {
    padding-left: 0;

    /deep/ .el-card__header {
      padding: 0 10px;
    }
  }
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

  .release-html {
    word-break: keep-all;

    /deep/ ul {
      padding-left: 20px;
    }
  }
}
</style>
