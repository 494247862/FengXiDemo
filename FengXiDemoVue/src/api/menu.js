import request from '@/utils/request'

// 获取当前用户的权限菜单树
export function getMenuTreesByCurrents() {
  return request({
    url: '/menu/getMenuTreesByCurrent',
    method: 'get'
  })
}

export function getMenuTreesByRoleId(roleId) {
  return request({
    url: `/menu/getMenuTreesByRoleId/${roleId}`,
    method: 'get'
  })
}

// 获取所有菜单树
export function getMenuTrees() {
  return request({
    url: '/menu/getMenuTrees',
    method: 'get'
  })
}

// 查询菜单
export function QueryMenuData(data) {
  return request({
    url: '/menu/QueryMenuData',
    method: 'post',
    data
  })
}

// 修改菜单
export function updateMenu(data) {
  return request({
    url: '/menu/updateMenu',
    method: 'post',
    data
  })
}

// 新增菜单
export function addMenu(data) {
  return request({
    url: '/menu/addMenu',
    method: 'post',
    data
  })
}

// 删除菜单
export function deleteMenu(id) {
  return request({
    url: `/menu/deleteMenu/${id}`,
    method: 'delete'
  })
}

// 获取当前用户菜单
export function getMenuByCurrent() {
  return request({
    url: `/menu/getMenuByCurrent`,
    method: 'get'
  })
}
