import Vue from 'vue';
import Vuex from 'vuex';

Vue.use(Vuex);
export const store = new Vuex.Store({
  state: {
    token: null,
    email: null,
    username: null,
    urls: [],
    startDate: null,
    endDate: null,
    serviceType: null,
    themeType: null,
    badgeType: 'percent',
    fetchedReleases: [],
    photoURL: null,
    baseURL: 'https://k5b201.p.ssafy.io/',
    basic: null,
    basic_folder_name: '',
    folders: [],
  },

  mutations: {},

  getters: {
    getStartDate(state) {
      return state.startDate;
    },

    getEmail(state) {
      return state.email;
    },

    getEndDate(state) {
      return state.endDate;
    },

    getServiceType(state) {
      return state.serviceType;
    },

    getThemeType(state) {
      return state.themeType;
    },

    getBadgeType(state) {
      return state.badgeType;
    },

    getFetchedReleases(state) {
      return state.fetchedReleases;
    },
    getToken(state) {
      return state.token;
    },
    getUsername(state) {
      return state.username;
    },
    getUrls(state) {
      return state.urls;
    },
    getPhotoUrl(state) {
      return state.photoURL;
    },
    getBaseUrl(state) {
      return state.baseURL;
    },
    getBasic(state) {
      return state.basic;
    },
    getBasicFolderName(state) {
      return state.basic_folder_name;
    },
    getFolders(state) {
      return state.folders;
    },
  },
  actions: {},
});
