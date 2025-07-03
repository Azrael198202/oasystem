import request from '@/utils/request'

//履歴経歴書一覧取得
export function searchResume(data) {
  return request({
    url: '/resume/getResumeList.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}

export function insertResume(data) {
  return request({
    url: '/resume/insertResume.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}

export function updateResume(data) {
  return request({
    url: '/resume/updateResume.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}

export function getResume(data) {
  return request({
    url: '/resume/getResume.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}

export function deleteResume(data) {
  return request({
    url: '/resume/deleteResume.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}

export function exportPDF(data) {
  return request({
    url: '/resume/exportPDF.do',
    headers: { 'content-type': 'application/json' },
    responseType : 'blob' ,
    method: 'post',
    data: data
  })
}

export function getResumeVerList(data) {
  return request({
    url: '/resume/getResumeVerList.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}

export function getUserList(data) {
  return request({
    url: '/resume/getUserList.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}
//一括ダウンロード（pdf）
export function exportPDFs(data) {
  return request({
    url: '/resume/exportPDFs.do',
    headers: { 'content-type': 'application/json' },
    responseType : 'blob' ,
    method: 'post',
    data: data
  })
}
