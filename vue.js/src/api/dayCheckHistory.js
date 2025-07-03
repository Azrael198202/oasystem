import request from '@/utils/request'

//日別チェック処理リスト
export function getDayCheckHistory(data) {
    return request({
      url: '/dayCheckHistory/getDayCheckHistory.do',
      headers: { 'content-type': 'application/json' },
      method: 'post',
      data: data
    })
  }

//日別チェック処理リスト検索
export function searchDayCheckHistory(data) {
  return request({
    url: '/dayCheckHistory/searchDayCheckHistory.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}
