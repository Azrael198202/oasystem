import request from '@/utils/request'

//お知らせ取得
export function searchNotice(data) {
  return request({
    url: '/notice/getNoticeAllList.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}

//お知らせ削除
export function deleteNotice(data) {
  return request({
    url: '/notice/deleteNotice.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}

export function insertNotice(data) {
  return request({
    url: '/notice/insertNotice.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}

export function getNoticeById(data) {
  return request({
    url: '/notice/getNoticeById.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}

export function updateNotice(data) {
  return request({
    url: '/notice/updateNotice.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}