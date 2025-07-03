import request from '@/utils/request'

// 勤务表详细取得（スケジュールを詳しく知る）
export function getAttendanceDetail(data) {
  return request({
    url: '/attendance/getDetail.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}

export function getApplyAttendance(data) {
  return request({
    url: '/attendance/selectByApply.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}
// 申請テーブル,休暇申請内容テーブル,休日出勤申請内容テーブル,振替出勤申請内容テーブル（グループメンバー取得）
export function getApplyAttendanceActivity(data) {
  return request({
    url: '/attendance/selectByApplyActivity.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}

// 勤务表设定情报取得
export function getAtndStting(data) {
  return request({
    url: '/attendance/getAtndStting.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}

// 勤務表テーブル＆勤務履歴テーブルに登録（グループメンバー取得　保存・申請）
export function attendanceCreate(data) {
  return request({
    url: '/attendance/create.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}
// 勤務表テーブル登録（グループメンバー取得　更新）
export function attendanceCreateUpdate(data) {
  return request({
    url: '/attendance/attendanceCreateUpdate.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}
// 他勤务表搜索
export function attendanceSearch(data) {
  return request({
    url: '/attendance/search.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}

// 勤务表出勤登録
export function workStart(data) {
  return request({
    url: '/attendance/workStart.do',
    headers: { 'content-type': 'application/json'},
    method: 'post',
    data: data
  })
}

// 勤务表退勤登録
export function workEnd(data) {
  return request({
    url: '/attendance/workEnd.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}

// 勤务表出勤登録2
export function workStartSetting(data) {
  return request({
    url: '/attendance/workStartSetting.do',
    headers: { 'content-type': 'application/json'},
    method: 'post',
    data: data
  })
}

// 勤务表退勤登録2
export function workEndSetting(data) {
  return request({
    url: '/attendance/workEndSetting.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}

// 勤务表excel
export function exportToProveExcel(data) {
  return request({
    url: '/attendance/exportToProveExcel.do',
    headers: { 'content-type': 'application/json' },
    responseType : 'blob' ,
    method: 'post',
    data: data
  })
}

// 勤务表Pdf出力
export function exportToAttendancePdfByEmployee(data) {
  return request({
    url: '/attendance/exportToAttendancePdf.do',
    headers: { 'content-type': 'application/json' },
    responseType : 'blob' ,
    method: 'post',
    data: data
  })
}