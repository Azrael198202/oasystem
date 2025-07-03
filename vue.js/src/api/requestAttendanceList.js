import request from '@/utils/request'
//グループメンバー取得
export function getRequestAttendanceList(data) {
    return request({
      url: '/requestAttendance/getRequestAttendance.do',
      headers: { 'content-type': 'application/json' },
      method: 'post',
      data: data
    })
  }

 //グループメンバー取得
export function getRequestAttendanceByList(data) {
  return request({
    url: '/requestAttendance/getRequestAttendanceBy.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
} 

//グループメンバー取得
export function insertRequestAttendance(data) {
  return request({
    url: '/requestAttendance/insertRequestAttendance.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}

export function updateRequestAttendance(data) {
  return request({
    url: '/requestAttendance/updateRequestAttendance.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}

export function updateNumRequestAttendance(data) {
  return request({
    url: '/requestAttendance/updateNumRequestAttendance.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}

export function deleteRequestAttendance(data) {
  return request({
    url: '/requestAttendance/deleteRequestAttendance.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}

export function getStatusAttendance(data) {
  return request({
    url: '/requestAttendance/getStatusAttendance.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}

