import request from '@/utils/request'

export function getPermissionTree(params) {
  return request({
    url: '/api/adminPrivelege/getAdminPermissionTree',
    method: 'get',
    params
  })
}

export function updateAdminRolePermissions(data) {
  return request({
    url: '/api/adminPrivelege/updateAdminRolePermissions',
    method: 'post',
    data
  })
}

