import request from '@/utils/request'

//ユーザー権限取得
export function getUserPermission(data) {
  return request({
    url: '/user/getUserPermission.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}
