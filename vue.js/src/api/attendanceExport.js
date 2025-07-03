import request from '@/utils/request'

// 一括出力画面で勤怠表検索押下時の処理（'年月と所属'または'社員番号or社員名'で検索）
export function searchBulkExport(data) {
  return request({
    url: '/attendance/searchBulkExport.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}
//勤务表一括ダウンロード（excel）
export function exportToProveExcel(data) {
  return request({
    url: '/attendance/exportToMultiProveExcel.do',
    headers: { 'content-type': 'application/json' },
    responseType : 'blob' ,
    method: 'post',
    data: data
  })
}
//勤务表一括ダウンロード（pdf）
 export function exportToProvePdfAttendance(data) {
   return request({
     url: '/attendance/exportToProveBulkPdf.do',
     headers: { 'content-type': 'application/json' },
     responseType : 'blob' ,
     method: 'post',
     data: data
   })
 }

 //月末精算一括ダウンロード（pdf）
 export function exportToProvePdfTransport(data) {
  return request({
    url: '/transportationExpenses/exportToProveBulkPdf.do',
    headers: { 'content-type': 'application/json' },
    responseType : 'blob' ,
    method: 'post',
    data: data
  })
}

// 申請状態の各ボタン押下後、PDFイメージストリームを作って、画面へ反映する
export function getAttendancePdfResource(data) {
  return request({
    url: '/attendance/exportToAttendancePdf.do',
    headers: { 'content-type': 'application/json' },
    responseType : 'blob' ,
    method: 'post',
    data: data
  })
}

// 申請状態の各ボタン押下後、PDFイメージストリームを作って、画面へ反映する
export function getTransportationExpenses(data) {
  return request({
    url: '/transportationExpenses/exportToPDF.do',
    headers: { 'content-type': 'application/json' },
    responseType : 'blob' ,
    method: 'post',
    data: data
  })

}