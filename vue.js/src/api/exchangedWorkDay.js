import request from '@/utils/request'

//休日出勤申請テーブルの保存
export function insertExchangedWorkDay(data) {
  return request({
    url: '/exchangedWorkDay/insertExchangedWorkDay.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}

export function selectExchangedWorkDay(data) {
  return request({
    url: '/exchangedWorkDay/selectExchangedWorkDay.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}

export function deleteExchangedWorkDay(data) {
  return request({
    url: '/exchangedWorkDay/deleteExchangedWorkDay.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}

export function exchangedWorkDayConfirm(data) {
  return request({
    url: '/exchangedWorkDay/exchangedWorkDayConfirm.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}