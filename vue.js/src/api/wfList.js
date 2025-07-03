import request from '@/utils/request'
//　既存データの確認
export function getWfList(data) {
    return request({
      url: '/requestWf/getWf.do',
      headers: { 'content-type': 'application/json' },
      method: 'post',
      data: data
    })
  }
//　最初に作るデータ（申請,承認）
export function insertNewWf(data) {
  return request({
    url: '/requestWf/insertNewWf.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}
// 承認
export function insertWf(data) {
  return request({
    url: '/requestWf/insertWf.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}
// 差戻
export function updateWf(data) {
  return request({
    url: '/requestWf/updateWf.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}
// 承認済
export function updateadmitWf(data) {
  return request({
    url: '/requestWf/updateadmitWf.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}
// 最終承認
export function updatelastadmitWf(data) {
  return request({
    url: '/requestWf/updatelastadmitWf.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}
//20210914
export function deleteWf(data) {
  return request({
    url: '/requestWf/deleteWf.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}

// ワークフロー全削除
export function deleteAllWf(data) {
  return request({
    url: '/requestWf/deleteAllWf.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}
// 差戻時の差戻以降のワークフローデータ全削除
export function deleteDataWf(data) {
  return request({
    url: '/requestWf/deleteDataWf.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}

