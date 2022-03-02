import { store } from 'quasar/wrappers'
import { createStore } from 'vuex'
import createPersistedState from 'vuex-persistedstate'

import user from './user'
import urls from './urls'
import recommend from './recommend'

let vuexStore = null;

export default store(function (/* { ssrContext } */) {
  const Store = createStore({
    modules: {
      user,
      urls,
      recommend
    },
    plugins: [createPersistedState({
      paths: ["recommend"]
    })],
    // enable strict mode (adds overhead!)
    // for dev mode and --debug builds only
    strict: process.env.DEBUGGING
  })

  vuexStore = Store
  return Store
})

export { vuexStore }