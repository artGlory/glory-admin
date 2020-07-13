import request from '@/utils/request'

export function getPlatformInfo() {
  return request({
    url: '/api/amdinSystem/getPlatformInfo',
    method: 'get'
  })
}

export function getPageConfig(data) {
  return request({
    url: '/api/amdinSystem/getPageConfig',
    method: 'post',
    data
  })
}

export function updateSystemConfig(data) {
  return request({
    url: '/api/amdinSystem/updateSystemConfig',
    method: 'post',
    data
  })
}

export function getGoogleAuthenticationDownload() {
  return request({
    url: '/api/amdinSystem/getGoogleAuthenticationDownload',
    method: 'get'
  })
}
