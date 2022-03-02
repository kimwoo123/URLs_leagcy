import Vue from 'vue';
import VueRouter from 'vue-router';
import ElementUI from 'element-ui';
import locale from 'element-ui/lib/locale/lang/ko';
import 'element-ui/lib/theme-chalk/index.css';
import dayjs from 'dayjs';
import {initializeApp} from 'firebase/app';
import {getAnalytics} from 'firebase/analytics';
import {store} from '../store/store';
import App from './App';
import routes from '../router/routes';

const firebaseConfig = {
  apiKey: 'AIzaSyBl3JirQQUJUC_t_9bCSaZG-q-XFM-fiTY',
  authDomain: 'urls-cf099.firebaseapp.com',
  projectId: 'urls-cf099',
  storageBucket: 'urls-cf099.appspot.com',
  messagingSenderId: '784803780405',
  appId: '1:784803780405:web:a5be70e311825849bd1e7c',
  measurementId: 'G-690JDSMZ4F',
};

const app = initializeApp(firebaseConfig);
const analytics = getAnalytics(app);

analytics.app.automaticDataCollectionEnabled = true;
global.browser = require('webextension-polyfill');

Vue.prototype.$browser = global.browser;
Vue.prototype.$dayjs = dayjs;

Vue.use(VueRouter);
Vue.use(ElementUI, {locale});

const router = new VueRouter({
  routes, // `routes: routes`의 줄임
});

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  dayjs,
  render: h => h(App),
});
