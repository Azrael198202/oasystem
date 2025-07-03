import request from '@/utils/request'
// 休暇申請
export function getRequestHolidayList(data) {
    return request({
      url: '/requestHoliday/getRequestHoliday.do',
      headers: { 'content-type': 'application/json' },
      method: 'post',
      data: data
    })
  }

// 休暇申請保存
export function insertRequestHolidayList(data) {
  return request({
    url: '/requestHoliday/insertRequestHoliday.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}

// 休暇申請削除
export function deleteRequestHoliday(data) {
  return request({
    url: '/requestHoliday/deleteRequestHoliday.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}

// 休暇申請更新
export function updateRequestHolidayList(data) {
  return request({
    url: '/requestHoliday/updateRequestHoliday.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}

// 重複データ確認
export function leaveDayConfirm(data) {
  return request({
    url: '/requestHoliday/leaveDayConfirm.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}

// 有給残数チェック
export function checkNum(data) {
  return request({
    url: '/requestHoliday/checkNum.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}

// 有給休暇履歴
export function paidHolidayHistory(data) {
  return request({
    url: '/requestHoliday/paidHolidayHistory.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}

// 有給休暇翌月・翌々月
export function paidHolidayNextMonth(data) {
  return request({
    url: '/requestHoliday/paidHolidayNextMonth.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}

// 代休当月
export function subHolidayHistory(data) {
  return request({
    url: '/requestHoliday/subHolidayHistory.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}

// 代休翌月・翌々月
export function subHolidayNextMonth(data) {
  return request({
    url: '/requestHoliday/subHolidayNextMonth.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}

//有給履歴・予定
export function RequestCompleteHoliday(data) {
  return request({
    url: '/requestHoliday/RequestCompleteHoliday.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data
  })
}
//代休残確認
export function countSubHoliday(data) {
  return request({
    url: '/requestHoliday/countSubHoliday.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}