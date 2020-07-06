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

export function updateAdminInfo(data) {
  return request({
    url: '/api/adminUser/updateAdminInfo',
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

export function getPageLoginLog(data) {
  return request({
    url: '/api/adminLoginLog/getPageLoginLog',
    method: 'post',
    data
  })
}

export function getPageOperateLog(data) {
  return request({
    url: '/api/adminOperateLog/getPageOperateLog',
    method: 'post',
    data
  })
}

