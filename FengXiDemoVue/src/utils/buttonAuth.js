// 按钮鉴权工具

import router from '@/router'

let btnAuth = (data) => {
  let name = router.app._route.name
  let btnAuth = JSON.parse(localStorage.getItem('userButtons'))
  let filter = btnAuth.filter(t => t.menuCode === name) // 先找菜单中是否有按钮
  let btn = filter.filter(t => t.buttonCode === data)// 再去找是否有按钮
  if (btn.length > 0) {
    return true
  } else {
    return false
  }
}

export default btnAuth
