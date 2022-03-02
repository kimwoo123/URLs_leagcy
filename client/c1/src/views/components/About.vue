<template>
  <div id="about">
    <div class="info">
      <!--      <img class="logo" src="/icons/128.png" />-->
      <h2 class="logo-title">{{ manifest.name }} v{{ manifest.version }}</h2>
      <p class="logo-description">똑똑하게 저장하고 나누는 방법</p>
    </div>
    <div class="developer">
      <h3 class="developer-title">지원 및 개발자 정보</h3>
      <div class="tab-info">
        <div class="tab-item">
          <a href="https://k5b201.p.ssafy.io/" target="_blank">
            <el-button icon="el-icon-s-flag" circle></el-button>
          </a>
          <br />
          <span class="item-title">웹사이트</span>
        </div>

        <div class="tab-item">
          <a href="https://lab.ssafy.com/s05-final/S05P31B201" target="_blank">
            <el-button circle>
              <img class="gitlab-logo" src="/icons/gitlab.png" />
            </el-button>
          </a>
          <br />
          <span class="item-title">GitLab</span>
        </div>
      </div>
    </div>
    <br />
    <br />
    <div class="status">현재 서버 상태: {{ status }}</div>
  </div>
</template>

<script>
import manifest from '../../manifest.json';
import MainApi from '../../api/mainApi';

export default {
  data() {
    return {
      manifest,
      status: false,
    };
  },
  methods: {
    async requestServerStatus() {
      const result = await MainApi.getServerStatus();
      this.status = result.status === 200 ? 'Live' : 'Dead';
    },
  },
  created() {
    this.requestServerStatus();
  },
};
</script>

<style lang="scss" scoped>
#about {
  display: flex;
  flex-direction: column;
  justify-content: center;
  vertical-align: middle;
  text-align: center;

  .info {
    .logo {
      width: 72px;
      height: 72px;
    }

    .logo-title {
      margin: 0;
    }

    .logo-description {
      margin: 0;
    }
  }

  .developer {
    .tab-info {
      display: flex;

      .tab-item {
        flex: 1;

        .gitlab-logo {
          width: 14px;
          height: 14px;
        }
      }
    }
  }
}
</style>
