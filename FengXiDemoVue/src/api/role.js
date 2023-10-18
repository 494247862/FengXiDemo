import request from '@/utils/request'

export function getAllRoles() {
  return request({
    url: '/role/getAllRoles',
    method: 'get'
  })
}

export function addRole(data) {
  return request({
    url: '/role/addRole',
    method: 'post',
    data
  })
}

export function updateRole(data) {
  return request({
    url: `/role/updateRole`,
    method: 'post',
    data
  })
}

export function deleteRole(id) {
  return request({
    url: `/role/deleteRole/${id}`,
    method: 'delete'
  })
}

export function QueryRolePage(data) {
  return request({
    url: `/role/QueryRolePage`,
    method: 'post',
    data
  })
}

export function getUserByCode(code) {
  return request({
    url: `/role/getUserByCode/${code}`,
    method: 'get'
  })
}
