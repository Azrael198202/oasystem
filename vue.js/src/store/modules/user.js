import { logout, getInfo, login } from '@/api/user'
import { agentLogin } from '@/api/login'

import {
  getToken,
  setToken,
  getUserInfo,
  setUserInfo,
  removeToken,
  setMemberUsername,
  setAgentUsername
} from '@/utils/auth'
import router, { resetRouter } from '@/router'

const state = {
    accountKbn: '',
    companyCode: '',
    permissionKbn: '',
    userId: '',
    userName: '',
    token: getToken()
}

// const state = {
//   token: getToken(),
//   name: '',
//   avatar: '',
//   introduction: '',
//   roles: [],
//   searchcondations: ''
// }

const mutations = {
  
  SET_ACCOUNTKBN: (state, accountKbn) => {
    state.accountKbn = accountKbn
  },
  SET_COMPANYCODE: (state, companyCode) => {
    state.companyCode = companyCode
  },
  SET_PERMISSIONKBN: (state, permissionKbn) => {
    state.permissionKbn = permissionKbn
  },
  SET_USERID: (state, userId) => {
    state.userId = userId
  },
  SET_USERNAME: (state, userName) => {
    state.userName = userName
  },
  SET_TOKEN: (state, token) => {
    state.token = token
  }
}

const actions = {
  // user login
  login({ commit }, response) {
    const { userInfo, token} = response
    return new Promise((resolve, reject) => {
      commit('SET_TOKEN', token)
      commit('SET_USERNAME', userInfo.userName)
      setToken('Admin-Token', token)
      setUserInfo(userInfo)
      // if (checked) {
      //   // if (isMember && isMember != undefined) {
      //   //   //记住会员用户名
      //   //   setMemberUsername({ username: userInfo.userName.trim() })
      //   // } else {
      //   // 记住经纪人用户名
      //   console.log("111")
      //   setAgentUsername({ username: userInfo.userName.trim() })
      //   // }
      // }
      resolve()
    })
  },
  // adminLogin
  adminLogin({ commit }, userInfo) {
    const { username, password, isMember } = userInfo
    return new Promise((resolve, reject) => {
      adminLogin({ username: username.trim(), password: password })
        .then(response => {
          const { data } = response
          setToken('Japansoufun-Token', data.token)
          resolve()
        })
        .catch(error => {
          reject(error)
        })
    })
  },
  // get user info
  getInfo({ commit, state }) {
    return new Promise((resolve, reject) => {
      getInfo(state.token)
        .then(response => {
          const { data } = response
          if (!data) {
            reject('Verification failed, please Login again.')
          }
          const { roles, name, avatar, introduction } = data
          // roles must be a non-empty array
          if (!roles || roles.length <= 0) {
            reject('getInfo: roles must be a non-null array!')
          }
          commit('SET_ROLES', roles)
          commit('SET_NAME', name)
          commit('SET_AVATAR', avatar)
          commit('SET_INTRODUCTION', introduction)
          resolve(data)
        })
        .catch(error => {
          reject(error)
        })
    })
  },

  // user logout
  logout({ commit, state, dispatch }) {
    return new Promise((resolve, reject) => {
      logout(state.token)
        .then(() => {
          commit('SET_TOKEN', '')
          commit('SET_USERNAME', '')
          // commit('SET_ROLES', [])
          removeToken()
          // resetRouter()

          // reset visited views and cached views
          // to fixed https://github.com/PanJiaChen/vue-element-admin/issues/2485
          // dispatch('tagsView/delAllViews', null, { root: true })

          resolve()
        })
        .catch(error => {
          reject(error)
        })
    })
  },

  // remove token
  resetToken({ commit }) {
    return new Promise(resolve => {
      commit('SET_TOKEN', '')
      commit('SET_ROLES', [])
      removeToken()
      resolve()
    })
  },

  // dynamically modify permissions
  async changeRoles({ commit, dispatch }, role) {
    const token = role + '-token'

    commit('SET_TOKEN', token)
    setToken(token)

    const { roles } = await dispatch('getInfo')

    resetRouter()

    // generate accessible routes map based on roles
    // const accessRoutes = await dispatch('permission/generateRoutes', roles, { root: true })
    // dynamically add accessible routes
    // router.addRoutes(accessRoutes)

    // reset visited views and cached views
    dispatch('tagsView/delAllViews', null, { root: true })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
