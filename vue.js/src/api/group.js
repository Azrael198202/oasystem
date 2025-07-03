import request from '@/utils/request'

//グループ取得
export function getGroup(data) {
  return request({
    url: '/group/getGroup.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}

//グループメンバー取得
export function getGroupMember(data) {
  return request({
    url: '/group/getDetails.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}

//グループ登録・更新
export function updateGroup(data) {
  return request({
    url: '/group/creat.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}

//グループメンバー削除
export function deleteGroup(data) {
  return request({
    url: '/group/delete.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}




