<template>
  <div id="sider">
    <el-menu :default-active="defaultActive" :router="true">
      <el-menu-item>
        <span>URLS</span>
      </el-menu-item>
      <el-menu-item
        v-for="route in routes"
        :key="route.name"
        :index="route.path"
      >
        <i :class="route.icon"></i>
        <span>{{ route.name }}</span>
      </el-menu-item>
    </el-menu>
  </div>
</template>

<script>
import showdown from 'showdown';
import mainApi from '../../api/mainApi';
import routes from '../../router/routes';
import {mixin} from '../../utils/mixin';

const converter = new showdown.Converter();

export default {
  data() {
    return {
      routes,
      fetchedReleases: [],
      folders: [],
      urls: [],
      token: '',
    };
  },

  methods: {
    initComponent() {
      this.token = this.$store.getters.getToken;
    },

    async fetchFolders() {
      this.loading = true;
      this.token = this.getFromStorage('token');
      const response = await mainApi.getFolders(this.token);
      if (response && response.data) {
        this.folders = response.data.map(folder => ({
          folder_id: folder.folder_id,
          folder_name: folder.folder_name,
          shared: folder.shared,
        }));
      }
      console.log(response);
      console.log('fetchFolder :', this.folders);
      this.save(
        {
          folders: this.folders,
        },
        'updated',
      );
    },

    async fetchReleaseNotes() {
      this.loading = true;
      const resp = await mainApi.getRelease();
      if (resp && resp.data) {
        this.fetchedReleases = resp.data.map(release => ({
          body: release.description,
          html: converter.makeHtml(release.body),
          created_at: release.date,
          id: release.title,
          name: release.version,
        }));
        console.log('fetchedReleases :', this.fetchedReleases);

        const payload = {
          fetchedReleases: this.fetchedReleases,
        };
        this.save(payload, 'updated');
      } else {
        throw new Error('No resp');
      }
      this.loading = false;
    },
  },

  computed: {
    defaultActive() {
      return this.$route.path;
    },
  },

  created() {
    this.fetchReleaseNotes();
    this.initComponent();
    this.fetchFolders();
    this.$root.$on('loaded:sider', () => {
      this.initComponent();
    });
  },

  beforeDestroy() {
    this.$root.$off('loaded:sider');
  },
  mixins: [mixin],
};
</script>

<style lang="scss" scoped>
@import '../../style/global.scss';

#sider {
  position: fixed;
  width: $sider-width;
  height: $sider-height;
  background-color: #f2f6fc;
}
</style>
