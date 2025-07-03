// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import store from './store'
import axios from 'axios'
import request from './utils/request' 
import './assets/css/global.css'

import fontawesome from '@fortawesome/fontawesome'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'
import solid from '@fortawesome/fontawesome-free-solid'
import regular from '@fortawesome/fontawesome-free-regular'
import brands from '@fortawesome/fontawesome-free-brands'

import ElementUI from 'element-ui';

import 'element-ui/lib/theme-chalk/index.css';
import VueAxios from "vue-axios";
import locale from 'element-ui/lib/locale/lang/ja'

Vue.use(ElementUI, { locale })
 

Vue.use(VueAxios, axios)


fontawesome.library.add(solid)
fontawesome.library.add(regular)
fontawesome.library.add(brands)
Vue.prototype.$baseUrl = process.env.baseUrl

Vue.component('font-awesome-icon', FontAwesomeIcon)

Vue.config.productionTip = false
Vue.prototype.$request = request

Vue.use(ElementUI);

const __build_timestamp__ = '__BUILD_TIME__';
console.log('Build timestamp:', __build_timestamp__);

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  components: { App },
  template: '<App/>'
})
