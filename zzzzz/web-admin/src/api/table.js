import request from '@/utils/request'

export function getList(params) {
  return request({
    url: '/api/table/list',
    method: 'get',
    params
  })
}
