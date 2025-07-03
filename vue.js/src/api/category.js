import request from '@/utils/request'

//勤务表出勤区分
export function getCategory(data) {
  return request({
    url: '/category/getCategory.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}

  
//勤务表休日情报取得
export function getHoliday(data) {
  return request({
    url: '/category/getHoliday.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}