import request from '@/utils/request'

//深夜残業申請テーブルの保存
export function insertData(data) {
  return request({
    url: '/overTime/insertData.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}
export function getRequestOvertime(data) {
  return request({
    url: '/overTime/getRequestOvertime.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}
export function deleteOvertime(data) {
  return request({
    url: '/overTime/deleteOvertime.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}

export function overtimeDayConfirm(data) {
  return request({
    url: '/overTime/overtimeDayConfirm.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}