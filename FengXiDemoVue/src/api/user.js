import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/user/login',
    method: 'post',
    data
  })
}

export function getInfo() {
  return request({
    url: '/user/getCurrentUser',
    method: 'post',
  })
}

export function logout() {
  return request({
    url: '/user/logout',
    method: 'post',
  })
}

export function addUser(data) {
  return request({
    url: '/user/addUser',
    method: 'post',
    data
  })
}

export function updateUser(data) {
  return request({
    url: '/user/updateUser',
    method: 'post',
    data
  })
}

export function getAllUser() {
  return request({
    url: '/user/getAllUser',
    method: 'post',
  })
}

export function deleteUser(id) {
  return request({
    url: `/user/deleteUser/${id}`,
    method: 'delete',
  })
}

export function queryUserData(data) {
  return request({
    url: `/user/queryUserData`,
    method: 'post',
    data
  })
}

export function updatePassword(data) {
  return request({
    url: `/user/updatePassword`,
    method: 'post',
    data
  })
}
