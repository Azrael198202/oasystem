import request from '@/utils/request'

//有休残テーブルの取得
export function getRequestPaidHoliday(data) {
  return request({
    url: '/paidHoliday/getRequestPaidHoliday.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}

//有休残テーブルの取得
export function EndMonthPaidHoliday(data) {
  return request({
    url: '/paidHoliday/EndMonthPaidHoliday.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}

//有休残テーブルの取得
export function EndMonthAfterNextPaidHoliday(data) {
  return request({
    url: '/paidHoliday/EndMonthAfterNextPaidHoliday.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}
//有休残メンテナンスのテーブル取得
export function getPaidHolidayMaintenance(data) {
  return request({
    url: '/paidHoliday/getPaidHolidayMaintenance.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}
//有休残テーブルのレコード作成処理
export function insertPaidHolidayRemaining(data) {
  return request({
    url: '/paidHoliday/insertPaidHolidayRemaining.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}
//有休残テーブルのレコード更新処理
export function updatePaidHolidayRemaining(data) {
  return request({
    url: '/paidHoliday/updatePaidHolidayRemaining.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}

export function getPaidHolidayRemaining(data) {
  return request({
    url: '/paidHoliday/getPaidHolidayRemaining.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}