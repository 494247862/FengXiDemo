import request from '@/utils/request'

// 导出样例
export function exportTest() {
  return request({
    url: '/excel/export',
    method: 'POST',
  })
}
