import axios from 'axios'
import { MessageBox, Message } from 'element-ui'
import store from '@/store'
import router from '@/router'
import { getToken } from '@/utils/auth'


// var root = process.env.API_ROOT
// create an axios instance
const service = axios.create({
  baseURL: process.env.API_ROOT, // url = base url + request url
  timeout: 600000 // request timeout
})
// console.log(root)
// console.log(axios.baseURL)


// request interceptor
service.interceptors.request.use(
  config => {
    // do something before request is sent`
    // do something before request is sent

    if (store.getters.token) {
      config.headers['Authorization'] = getToken() // 让每个请求携带自定义token 请根据实际情况自行修改
    }
    return config
  },
  error => {
    // do something with request error
    console.log(error) // for debug
    return Promise.reject(error)
  }
)

// response interceptor
service.interceptors.response.use(
  /**
   * If you want to get http information such as headers or status
   * Please return  response => response
   */

  /**
   * Determine the request status by custom code
   * Here is just an example
   * You can also judge the status by HTTP Status Code
   */
  response => {
    const res = response.data
    if(res != null && res.status == null){
      return response;
    }
    // if the custom code is not 20000, it is judged as an error.
    if (
      res.status !== 200 &&
      res.status !== 601 &&
      res.status !== 602 &&
      res.status !== 404 &&
      res.status !== 400 &&
      res.status !== 423
    ) {
      Message({
        message: res.msgContent || 'Error',
        type: 'error',
        duration: 5 * 1000
      })
      // 50008: Illegal token; 50012: Other clients logged in; 50014: Token expired;
      if (res.status === 500) {
        // businessMessage.messageBox('')
      }
      return Promise.reject(new Error(res.message || 'Error'))
    } else {
      return res
    }
  },
  async error => {
    console.log('err' + error) // for debug
    if(error.response.status == 401) {
      Message({
        message: 'ログインの有効期限が切れました。もう一度ログインしてください',
        type: 'error',
        duration: 4 * 1000
      })
      await store.dispatch('user/logout');
      router.push('/login')
    }else {
      Message({
        message: error.message,
        type: 'error',
        duration: 5 * 1000
      })
    }
    
    return Promise.reject(error)
  }
)

export default service
