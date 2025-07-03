import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/vue-element-admin/user/login',
    method: 'post',
    data
  })
}

export function getInfo(data) {
  return request({
    url: '/user/getInfo.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data
  })
}

export function getUserDetail(data) {
  return request({
    url: '/user/getUserDetail.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data
  })
}

export function updateAvatar(data) {
  return request({
    url: '/user/upload-avatar.do',
    headers: { },
    method: 'post',
    data
  })
}

export function setUserDetail(data) {
  return request({
    url: '/user/update.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data
  })
}

export function findPwdMail(data) {
  return request({
    url: '/user/findPwdMail.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data
  })
}

export function findPwd(data) {
  return request({
    url: '/user/findPwd.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data
  })
}

export function logout() {
  return request({
    url: '/login/logout.do',
    method: 'post',
    headers: { 'Content-Type': 'application/json' }
  })
}

export function getNoticeList(data) {
  return request({
    url: '/attendance/getNoticeList.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data
  })
}

export function getNoticeDetil(data) {
  return request({
    url: '/attendance/getNoticeDetil.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data
  })
}

export function getButton(data) {
  return request({
    url: '/user/getUserPermission.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data
  })
}

export function resetPassWordAllUser(data) {
  return request({
    url: '/user/resetPassWordAllUser.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data
  })
}

// 利用者マスタ（基本情報）テーブルの更新処理
export function authorityCreate(data) {
  return request({
    url: '/user/authorityCreate.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}
// 社員番号or社員名で検索（グループメンバー取得）
export function research(data) {
  return request({
    url: '/user/research.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}

// リセット
export function resetPwd(data) {
  return request({
    url: '/user/resetPwd.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}


