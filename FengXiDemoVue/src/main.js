import Vue from 'vue'

import 'normalize.css/normalize.css' // A modern alternative to CSS resets

import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import enLocale from 'element-ui/lib/locale/lang/en' // lang i18n
import cnLocale from 'element-ui/lib/locale/lang/zh-CN' // lang i18n

import '@/styles/index.scss' // global css

import App from './App'
import store from './store'
import router from './router'

import '@/icons' // icon
import '@/permission' // permission control
import btnAuth from '@/utils/buttonAuth'
import {divw, divh} from "@/utils/fullUtil"; // 按钮鉴权
/**
 * If you don't want to use mock-server
 * you want to use MockJs for mock api
 * you can execute: mockXHR()
 *
 * Currently MockJs will be used in the production environment,
 * please remove it before going online ! ! !
 */
// if (process.env.NODE_ENV === 'production') {
//   const { mockXHR } = require('../mock')
//   mockXHR()
// }

// set ElementUI lang to EN
Vue.use(ElementUI, {cnLocale})
// 如果想要中文版 element-ui，按如下方式声明
// Vue.use(ElementUI)

Vue.config.productionTip = false
Vue.prototype.$authButton = btnAuth
Vue.prototype.$divw = divw
Vue.prototype.$divh = divh
new Vue({
  el: '#app',
  router,
  store,
  data() {
    return {
      dh: this.divh(1),
      dw: this.divw(1),
      webStocketMessage: false // 用于传递监听
    }
  },
  methods: {
    divw(res) {
      const clientWidth = document.documentElement.clientWidth
      if (!clientWidth) return
      const fs = (clientWidth / 1920)
      return res * fs
    },
    divh(res) {
      const clientHeight = document.documentElement.clientHeight
      if (!clientHeight) return
      const fs = (clientHeight / 1080)
      return res * fs
    }
  },
  render: h => h(App)
})
