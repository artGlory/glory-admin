import { asyncRoutes, constantRoutes } from '@/router'

/**
 * Use meta.permission to determine if the current user has permission
 * @param permissions
 * @param route
 */
function hasPermission(permissions, route) {
  if (route.meta && route.meta.permissions) {
    return permissions.some(permission => route.meta.permissions.includes(permission))
  } else {
    return true
  }
}

/**
 * Filter asynchronous routing tables by recursion
 * @param routes asyncRoutes
 * @param permissions
 */
export function filterAsyncRoutes(routes, permissions) {
  const res = []

  routes.forEach(route => {
    const tmp = { ...route }
    if (hasPermission(permissions, tmp)) {
      if (tmp.children) {
        tmp.children = filterAsyncRoutes(tmp.children, permissions)
      }
      res.push(tmp)
    }
  })

  return res
}

/**
 * 获取元素在数组中的下表
 * @param {数组} arr
 * @param {元素} obj
 */
function getArrayIndex(arr, obj) {
  var i = arr.length
  while (i--) {
    if (arr[i] === obj) {
      return i
    }
  }
  return -1
}

/**
 * 按照权限的下标排序
 * @param {动态路由} routes
 * @param {权限} permissions
 */
export function sortAsyncRoutes(routes, permissions) {
  routes.sort(function(a, b) {
    let aIndex = 1000
    if (a.redirect === '/404') {
      aIndex = 10000
    }
    if (a.meta) {
      aIndex = 900
    }
    if (a.meta && a.meta.permissions) {
      aIndex = getArrayIndex(permissions, a.meta.permissions[0])
      if (aIndex === -1) {
        aIndex = 800
      }
    }

    let bIndex = 1000
    if (b.redirect === '/404') {
      bIndex = 10000
    }
    if (b.meta) {
      bIndex = 900
    }
    if (b.meta && b.meta.permissions) {
      bIndex = getArrayIndex(permissions, b.meta.permissions[0])
      if (bIndex === -1) {
        bIndex = 800
      }
    }

    if (a.children && a.children.length > 0) {
      sortAsyncRoutes(a.children, permissions)
    }
    if (b.children && b.children.length > 0) {
      sortAsyncRoutes(b.children, permissions)
    }
    return aIndex - bIndex
  })
}

const state = {
  routes: [],
  addRoutes: []
}

const mutations = {
  SET_ROUTES: (state, routes) => {
    state.addRoutes = routes
    state.routes = constantRoutes.concat(routes)
  }
}

const actions = {
  generateRoutes({ commit }, permissions) {
    return new Promise(resolve => {
      // let accessedRoutes
      // if (roles.includes('admin1')) {
      //   accessedRoutes = asyncRoutes || []
      // } else {
      //   accessedRoutes = filterAsyncRoutes(asyncRoutes, roles)
      // }
      const accessedRoutes = filterAsyncRoutes(asyncRoutes, permissions)
      sortAsyncRoutes(accessedRoutes, permissions)
      commit('SET_ROUTES', accessedRoutes)
      resolve(accessedRoutes)
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
