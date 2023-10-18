import request from '@/utils/request'

export function getImage(id) {
  return request({
    url: `/file/getImage/${id}`,
    method: 'get',
    responseType: 'blob'
  })
}

export function uploadFile(data) {
  return request({
    url: `/file/uploadFile`,
    method: 'post',
    data
  })
}

export function deleteFile(id) {
  return request({
    url: `/file/deleteFile/${id}`,
    method: 'delete'
  })
}
