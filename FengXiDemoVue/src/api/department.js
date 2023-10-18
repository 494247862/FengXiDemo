import request from '@/utils/request'

export function addDepartment(data) {
  return request({
    url: '/department/addDepartment',
    method: 'POST',
    data
  })
}

export function getDepartmentTree() {
  return request({
    url: '/department/getDepartmentTree',
    method: 'POST'
  })
}

export function updateDepartment(data) {
  return request({
    url: '/department/updateDepartment',
    method: 'POST',
    data
  })
}

export function deleteDepartment(id) {
  return request({
    url: `/department/deleteDepartment/${id}`,
    method: 'DELETE'
  })
}
