import request from '@/utils/request'

// 点击发送邮件
export function sendMailApi(data) {
  return request({
    url: '/user/findPwdMail.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}


export function sendreqMailApi(data) {
  return request({
    url: '/user/reqMail.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}

// 重置密码
export function savePwd(data) {
  return request({
    url: '/user/reset.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}
