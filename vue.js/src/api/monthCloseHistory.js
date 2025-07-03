import request from '@/utils/request'

//月締処理チェック
export function selectTgtMonth(data) {
    return request({
      url: '/monthCloseHistory/selectTgtMonth.do',
      headers: { 'content-type': 'application/json' },
      method: 'post',
      data: data
    })
}

export function paidHolidayHistoryMonth(data) {
  return request({
    url: '/monthCloseHistory/paidHolidayHistoryMonth.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}