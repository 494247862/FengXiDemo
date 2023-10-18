import request from '@/utils/request'

//获取当前用户的权限按钮
export function getButtonByCurrent() {
  return request({
    url: '/button/getButtonByCurrent',
    method: 'get'
  })
}

// 通过菜单id获取按钮数据
export function getButtonByMenuId(id) {
  return request({
    url: `/button/getButtonByMenuId/${id}`,
    method: 'get'
  })
}

// 新增按钮
export function addButton(data) {
  return request({
    url: `/button/addButton`,
    method: 'post',
    data
  })
}

// 删除按钮
export function deleteButton(id) {
  return request({
    url: `/button/deleteButton/${id}`,
    method: 'delete'
  })
}

// 修改按钮
export function updateButton(data) {
  return request({
    url: `/button/updateButton`,
    method: 'post',
    data
  })
}

// 获取菜单按钮树结构
export function getMenuButtonTree() {
  return request({
    url: `/button/getMenuButtonTree`,
    method: 'get',
  })
}
