import request from '@/utils/request'

// 手当申請：詳細処理
export function getRequestAllowance(data) {
  return request({
    url: '/requestAllowance/getRequestAllowance.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}
// 手当申請：レコード削除処理
export function deleteRequestAllowance(data) {
  return request({
    url: '/requestAllowance/deleteRequestAllowance.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}
// 手当申請：出張,リーダー手当の保存処理
export function insertAllowance(data) {
  return request({
    url: '/requestAllowance/insertAllowance.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}
// 手当申請：参照処理
export function searchReAllowanceMonthData(data) {
  return request({
    url: '/requestAllowance/searchReAllowanceMonthData.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}
// 手当申請:年月取得
export function searchMonth(data) {
  return request({
    url: '/requestAllowance/searchMonth.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}
// 手当申請：資格手当の保存処理
export function insertQualificationAllowance(data) {
  return request({
    url: '/requestAllowance/insertQualificationAllowance.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}
// 手当申請：資格手当のチェック処理
export function qualificationAllowanceCheck(data) {
  return request({
    url: '/requestAllowance/qualificationAllowanceCheck.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}
// 手当申請：慶弔見舞金申請の保存処理
export function insertCongratulatoryOrCondolencePayments(data) {
  return request({
    url: '/requestAllowance/insertCongratulatoryOrCondolencePayments.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}
//PDFSample
export function exportToPDF(data) {
  return request({
    url: '/requestAllowance/exportToPDF.do',
    headers: { 'content-type': 'application/json' },
    responseType : 'blob' ,
    method: 'post',
    data: data
  })
}