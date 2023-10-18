import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },
  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },
  {
    path: '/',
    redirect: '/First',
    component: Layout,
    children: [
      {
        path: '/First',
        name: 'First',
        component: () => import('@/views/First/index'),
        meta: {title: '首页', affix: true}
      },
      {
        path: '/MenuManage',
        component: () => import('@/views/MenuManage/index'),
        name: 'MenuManage',
        meta: {title: '菜单管理',noCache: true}
      },
      {
        path: '/RoleManage',
        component: () => import('@/views/RoleManage/role'),
        name: 'RoleManage',
        meta: {title: '角色管理'}
      },
      {
        path: '/UserManage',
        component: () => import('@/views/UserManage/index'),
        name: 'UserManage',
        meta: {title: '用户管理'}
      },
      {
        path: '/DepartmentManage',
        component: () => import('@/views/DepartmentManage/index'),
        name: 'DepartmentManage',
        meta: {title: '部门管理',noCache: true}
      },
      {
        path: '/ExcelExample',
        component: () => import('@/views/ExcelExample/index'),
        name: 'ExcelExample',
        meta: {title: '报表例子', noCache: true}
      }
    ]
  },
  {path: '*', redirect: '/404', hidden: true}
]

const createRouter = () =>
  new Router({
    // mode: 'history', // require service support
    // scrollBehavior: () => ({ y: 0 }),
    routes: constantRoutes
  })

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
