import request from '@/utils/request'
//代休末残取得
export function getRequestSubHoliday(data) {
  return request({
    url: '/subHoliday/getRequestSubHoliday.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}
//当月末残取得
export function endOfThisMonthSubHoliday(data) {
  return request({
    url: '/subHoliday/endOfThisMonthSubHoliday.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}
//翌々月末残取得
export function theEndOfTheFollowingMonthSubHoliday(data) {
  return request({
    url: '/subHoliday/theEndOfTheFollowingMonthSubHoliday.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}
//代休残取得
export function checkNum(data) {
  return request({
    url: '/subHoliday/checkNum.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}
//休日出勤日取得
export function getScheduledWorkDays(data) {
  return request({
    url: '/subHoliday/getScheduledWorkDays.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}
// 社員番号or社員名で検索（グループメンバー取得）
export function usersearch(data) {
  return request({
    url: '/subHoliday/usersearch.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}
// 代休残テーブル（重複データ確認）
export function checkSubHoliday(data) {
  return request({
    url: '/subHoliday/checkSubHoliday.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}
// 代休残テーブルに登録 ＆ 休日出勤申請内容テーブル（作成, 更新）
export function subHolidayCreate(data) {
  return request({
    url: '/subHoliday/subHolidayCreate.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}