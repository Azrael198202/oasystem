import request from '@/utils/request'
//有休修正テーブル処理
export function paidHolidayCorrect(data) {
    console.log(data)
    return request({
      url: '/paidHolidayCorrect/paidHolidayCorrect.do',
      headers: { 'content-type': 'application/json' },
      method: 'post',
      data: data
    })
  }
  export function getpaidHolidayCorrect(data) {
    console.log(data)
    return request({
      url: '/paidHolidayCorrect/getPaidHolidayCorrect.do',
      headers: { 'content-type': 'application/json' },
      method: 'post',
      data: data
    })
  }