import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/api/adminUser/adminLogin',
    method: 'post',
    data
  })
}
// 通过token获取admin信息
export function getInfo() {
  return request({
    url: '/api/adminUser/getAdminInfo',
    method: 'get'
  })
}

export function logout() {
  return request({
    url: '/api/adminUser/adminLogout',
    method: 'get'
  })
}

export function getPageAdmin(data) {
  return request({
    url: '/api/adminUser/getPageAdminInfo',
    method: 'post',
    data
  })
}

export function updateAdminRole(data) {
  return request({
    url: '/api/adminUser/updateAdminRole',
    method: 'post',
    data
  })
}

export function addAdminInfo(data) {
  return request({
    url: '/api/adminUser/addAdminInfo',
    method: 'post',
    data
  })
}

export function delAdminInfo(params) {
  return request({
    url: '/api/adminUser/delAdminInfo',
    method: 'get',
    params
  })
}

export function updateAdminPassword(data) {
  return request({
    url: '/api/adminUser/updateAdminPassword',
    method: 'post',
    data
  })
}

export function updateSelfPassword(data) {
  return request({
    url: '/api/adminUser/updateSelfPassword',
    method: 'post',
    data
  })
}

export function getSelfOperateTimeline() {
  return request({
    url: '/api/adminUser/getSelfOperateTimeline',
    method: 'get'
  })
}

export function getGoogleKey() {
  return request({
    url: '/api/adminUser/getGoogleKey',
    method: 'get'
  })
}

export function bindGoogleKey(data) {
  return request({
    url: '/api/adminUser/bindGoogleKey',
    method: 'post',
    data
  })
}

export function loginWithGooleAuthentication(params) {
  return request({
    url: '/api/adminUser/loginWithGooleAuthentication',
    method: 'get',
    params
  })
}

export function forbidAdminLogin(params) {
  return request({
    url: '/api/adminUser/forbidAdminLogin',
    method: 'get',
    params
  })
}

export function deleteAdminGoogleKey(params) {
  return request({
    url: '/api/adminUser/deleteAdminGoogleKey',
    method: 'get',
    params
  })
}
