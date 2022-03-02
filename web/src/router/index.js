import { route } from 'quasar/wrappers'
import { createRouter, createMemoryHistory, createWebHistory, createWebHashHistory } from 'vue-router'
import routes from './routes'

let vueRouter = null;

export default route(function ({ store }) {
  const createHistory = process.env.SERVER
    ? createMemoryHistory
    : (process.env.VUE_ROUTER_MODE === 'history' ? createWebHistory : createWebHashHistory)

  const Router = createRouter({
    scrollBehavior: () => ({ left: 0, top: 0 }),
    routes,

    // Leave this as is and make changes in quasar.conf.js instead!
    // quasar.conf.js -> build -> vueRouterMode
    // quasar.conf.js -> build -> publicPath
    history: createHistory(process.env.MODE === 'ssr' ? void 0 : process.env.VUE_ROUTER_BASE)
  })

  // 전역가드
  Router.beforeEach(function (to, from, next) {
    // 로그인을 요구하는 페이지로 이동하는데 로그인이 안되어있으면
    if (to.meta.auth && !store.getters['user/isLogin']) {
      next({ name: 'BeforeLogin' })
    } else if (!to.meta.auth && store.getters['user/isLogin']) {
      next({ name : 'AllUrls', params: { id: store.state.user.userid }})
    } else {
      next()
    }
  })

  vueRouter = Router

  return Router
})

export { vueRouter }
