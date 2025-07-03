import request from '@/utils/request'

//用户请求登录
export function agentLogin(data) {
  return request({
    url: '/login/login.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}

//用户请求登录
export function demoRes(data) {
  return request({
    url: '/request/getRequest.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}
