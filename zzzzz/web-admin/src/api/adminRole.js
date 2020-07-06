import request from '@/utils/request'

export function getRolesTree() {
  return request({
    url: '/api/adminRole/getRolesTree',
    method: 'get'
  })
}

export function updateRoleInfo(data) {
  return request({
    url: '/api/adminRole/updateRole',
    method: 'post',
    data
  })
}

export function deleteRoleInfo(params) {
  return request({
    url: '/api/adminRole/deleteRole',
    method: 'get',
    params
  })
}

export function addRoleInfo(data) {
  return request({
    url: '/api/adminRole/addRole',
    method: 'post',
    data
  })
}

export function getAllSelfSubRole() {
  return request({
    url: '/api/adminRole/getAllSelfSubRole',
    method: 'get'
  })
}
