import request from '@/utils/request'

//休日出勤申請テーブルの保存
export function insertWorkDayOff(data) {
  return request({
    url: '/workDayOff/insertWorkDayOff.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}

export function selectWorkDayOff(data) {
  return request({
    url: '/workDayOff/selectWorkDayOff.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}

export function deleteWorkDayOff(data) {
  return request({
    url: '/workDayOff/deleteWorkDayOff.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}
// 休日出勤テーブル申請番号取得
export function requestNumberSubHoliday(data) {
  return request({
    url: '/workDayOff/requestNumberSubHoliday.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}
// 休日出勤申請データ取得
export function getWorkDayOffInfo(data) {
  return request({
    url: '/workDayOff/getWorkDayOffInfo.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}