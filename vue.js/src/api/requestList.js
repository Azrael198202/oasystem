import request from '@/utils/request'
//グループメンバー取得
export function getRequestList(data) {
    return request({
      url: '/request/getRequest.do',
      headers: { 'content-type': 'application/json' },
      method: 'post',
      data: data
    })
  }

//勤怠申請（申請/申請状態区分＝回覧中：003）
export function insertRequest(data) {
  return request({
    url: '/request/insertRequest.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}

//その他申請保存
export function insertLeaveRequest(data) {
  return request({
    url: '/request/insertLeaveRequest.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}

//取戻（申請テーブル回覧中→保存）
export function updateReturnRequest(data) {
  return request({
    url: '/request/updateReturnRequest.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}

//グループメンバー取得
export function updateSkRequest(data) {
  return request({
    url: '/request/updateSkRequest.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}
//グループメンバー取得
export function updateLaRequest(data) {
  return request({
    url: '/request/updateLaRequest.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}

export function deleteRequest(data) {
  return request({
    url: '/request/deleteRequest.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}

//申請状態と申請種類の検索
export function searchRequest(data) {
  return request({
    url: '/request/search.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}