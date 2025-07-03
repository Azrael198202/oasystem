import request from '@/utils/request'

export function getDepartment(data) {
  return request({
    url: '/department/getDepartment.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}