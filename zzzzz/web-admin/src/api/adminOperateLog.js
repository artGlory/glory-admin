import request from '@/utils/request'

export function getPageOperateLog(data) {
  return request({
    url: '/api/adminOperateLog/getPageOperateLog',
    method: 'post',
    data
  })
}
