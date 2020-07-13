import request from '@/utils/request'

export function getPageLoginLog(data) {
  return request({
    url: '/api/adminLoginLog/getPageLoginLog',
    method: 'post',
    data
  })
}
