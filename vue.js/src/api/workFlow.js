import request from '@/utils/request'
//グループメンバー取得
export function getWorkFlowList(data) {
    return request({
      url: '/requestWf/getWf.do',
      headers: { 'content-type': 'application/json' },
      method: 'post',
      data: data
    })
  }

export function getSelectWf(data) {
  return request({
    url: '/requestWf/selectWf.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}
 