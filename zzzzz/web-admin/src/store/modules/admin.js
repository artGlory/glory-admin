import { login, logout, getInfo } from '@/api/admin'
import { getToken, setToken, removeToken } from '@/utils/auth'
import { resetRouter } from '@/router'

const getDefaultState = () => {
  return {
    token: getToken(),
    name: '',
    avatar: '',
    permissions: [],
    system: {
      name: '',
      logo: '',
      copyright: ''
    },
    isBindGoogleAuthentication: '',
    isLoginWithGoogleAuthentication: ''
  }
}

const state = getDefaultState()

const mutations = {
  RESET_STATE: (state) => {
    Object.assign(state, getDefaultState())
  },
  SET_TOKEN: (state, token) => {
    state.token = token
  },
  SET_NAME: (state, name) => {
    state.name = name
  },
  SET_AVATAR: (state, avatar) => {
    state.avatar = avatar
  },
  SET_PERMISSIONS: (state, permissions) => {
    state.permissions = permissions
  },
  SET_SYSTEM_NAME: (state, name) => {
    state.system.name = name
  },
  SET_SYSTEM_LOGO: (state, logo) => {
    state.system.logo = logo
  },
  SET_SYSTEM_COPYRIGHT: (state, copyright) => {
    state.system.copyright = copyright
  },
  SET_BING_GOOGLE_AUTHENTICATION: (state, isBindGoogleAuthentication) => {
    state.isBindGoogleAuthentication = isBindGoogleAuthentication
  },
  SET_LOGIN_WITH_GOOGLE_AUTHENTICATION: (state, isLoginWithGoogleAuthentication) => {
    state.isLoginWithGoogleAuthentication = isLoginWithGoogleAuthentication
  }
}

const actions = {
  // admin login
  login({ commit }, adminInfo) {
    const { username, password, googleCode } = adminInfo
    return new Promise((resolve, reject) => {
      login({ username: username.trim(), password: password, googleCode: googleCode }).then(response => {
        const { data } = response
        if (data.isNeedGoogleCode) {
          resolve({ needGoogleCode: data.isNeedGoogleCode })
        } else {
          commit('SET_TOKEN', data.token) // context.commit('方法名','参数')
          setToken(data.token)
          resolve({ needGoogleCode: data.isNeedGoogleCode })
        }
      }).catch(error => {
        reject(error)
      })
    })
  },

  // get admnin info
  getInfo({ commit, state }) {
    return new Promise((resolve, reject) => {
      getInfo(state.token).then(response => {
        const { data } = response
        if (!data) {
          reject('验证失败，请重新登陆')
        }

        const { permissions, name, avatar, systemName, systemLogo, systemCopyright, isBindGoogleAuthentication, isLoginWithGoogleAuthentication } = data

        // permissions must be a non-empty array
        if (!permissions || permissions.length <= 0) {
          commit('RESET_STATE')
          reject('用户权限不能为空')
        }

        commit('SET_PERMISSIONS', permissions)
        commit('SET_NAME', name)
        commit('SET_AVATAR', avatar)
        commit('SET_SYSTEM_NAME', systemName)
        commit('SET_SYSTEM_LOGO', systemLogo)
        commit('SET_SYSTEM_COPYRIGHT', systemCopyright)
        commit('SET_BING_GOOGLE_AUTHENTICATION', isBindGoogleAuthentication)
        commit('SET_LOGIN_WITH_GOOGLE_AUTHENTICATION', isLoginWithGoogleAuthentication)
        resolve(data)
      }).catch(error => {
        reject(error)
      })
    })
  },

  // admnin logout
  logout({ commit, state }) {
    return new Promise((resolve, reject) => {
      logout(state.token).then(() => {
        removeToken() // must remove  token  first
        resetRouter()
        commit('RESET_STATE')
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },

  // remove token
  resetToken({ commit }) {
    return new Promise(resolve => {
      removeToken() // must remove  token  first
      commit('RESET_STATE')
      resolve()
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}

