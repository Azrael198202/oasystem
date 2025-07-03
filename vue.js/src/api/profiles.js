export function getOldPassword(data) {
  return request({
    url: '/profiles/getOldPassword.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}

export function getProfileImage(data) {
  return request({
    url: '/profiles/getProfileImae.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}

export function postData(data) {
  return request({
    url: '/profiles/postData.do',
    headers: { 'content-type': 'application/json' },
    method: 'post',
    data: data
  })
}