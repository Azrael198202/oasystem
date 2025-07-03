import Cookies from 'js-cookie'

const TokenKey = 'Admin-Token'
const userInfoKey = 'userInfo-key'
const userDetailedInfoKey = 'userDetailedInfo-key'
const memeberUsername = 'member-Username'
const agentUsername = 'agentUsername'
const adminUsername = 'adminUsername'

export function getToken() {
  return Cookies.get(TokenKey)
}

export function setToken(key, token) {
  return Cookies.set(key, token)
}

export function getUserInfo() {
  var infoStr = Cookies.get(userInfoKey)
  if (infoStr == null || infoStr == '') {
    return null
  }
  return JSON.parse(infoStr)
}

export function setUserInfo(userInfo) {
  return Cookies.set(userInfoKey, userInfo)
}

export function getUserDetailedInfo() {
  var infoStr = Cookies.get(userDetailedInfoKey)
  if (infoStr == null || infoStr == '') {
    return null
  }
  return JSON.parse(infoStr)
}

export function setUserDetailedInfo(userInfo) {
  return Cookies.set(userDetailedInfoKey, userInfo)
}

export function removeToken() {
  Cookies.remove(userInfoKey)
  Cookies.remove(userDetailedInfoKey)
  return Cookies.remove(TokenKey)
}
//记住会员用户名
export function setMemberUsername(loginForm) {
  return Cookies.set(memeberUsername, loginForm, { expires: 30 })
}
//获取记住的会员用户名
export function getMemeberUsername() {
  return Cookies.getJSON(memeberUsername)
}
//获取记住的经纪人用户名
export function getAgentUsername() {
  return Cookies.getJSON(agentUsername)
}

//记住经纪人用户名
export function setAgentUsername(loginForm) {
  return Cookies.set(agentUsername, loginForm, { expires: 30 })
}

//记住管理员用户名
export function setAdminUsername(loginForm) {
  return Cookies.set(adminUsername, loginForm, { expires: 30 })
}
//获取记住的管理员用户名
export function getAdminUsername() {
  return Cookies.getJSON(adminUsername)
}
