import request from '@/utils/request'
// 交通費申請保存
export function insertTransportationExpenses(data) {
    return request({
      url: '/transportationExpenses/insertTransportationExpenses.do',
      headers: { 'content-type': 'application/json' },
      method: 'post',
      data: data
    })
  }

// 交通費申請内容取得
export function getTransportationExpenses(data) {
  return request({
    url: '/transportationExpenses/getTransportationExpenses.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}

// 交通費申請削除
export function deleteTransportationExpenses(data) {
  return request({
    url: '/transportationExpenses/deleteTransportationExpenses.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}

// 交通費申請更新
export function updateTransportationExpenses(data) {
  return request({
    url: '/transportationExpenses/updateTransportationExpenses.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}

// 参照年月検索
export function searchRefMonthData(data) {
  return request({
    url: '/transportationExpenses/searchRefMonthData.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}

// 過去申請の最大年月の取得
export function selectMonth(data) {
  return request({
    url: '/transportationExpenses/selectMonth.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}

//PDFSample
export function exportToPDF(data) {
  return request({
    url: '/transportationExpenses/exportToPDF.do',
    headers: { 'content-type': 'application/json' },
    responseType : 'blob' ,
    method: 'post',
    data: data
  })
}
// 一時用イメージファイル保存
export function saveImgTemporary(data) {
  return request({
    url: '/transportationExpenses/saveImgTemporary.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}

// 一時用イメージファイル削除
export function deleteImageTemporary(data) {
  return request({
    url: '/transportationExpenses/deleteImageTemporary.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}