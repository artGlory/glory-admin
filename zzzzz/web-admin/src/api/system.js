import request from '@/utils/request'

export function getPlatformInfo() {
  return request({
    url: '/api/amdinSystem/getPlatformInfo',
    method: 'get'
  })
}
