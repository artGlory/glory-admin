import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    permissions: ['permission1','permission2']    control the page permissions (you can set multiple permissions)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'/'el-icon-x' the icon show in the sidebar
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all can be accessed
 */
export const constantRoutes = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/login/index'),
    hidden: true
  },

  {
    path: '/404',
    name: 'Page404',
    component: () => import('@/views/404'),
    hidden: true
  },

  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [{
      path: '/dashboard',
      name: 'Dashboard',
      component: () => import('@/views/dashboard/index'),
      meta: { title: '仪表盘', icon: 'dashboard' }
    }]
  }

]

/**
 * asyncRoutes
 * the routes that need to be dynamically loaded based on user roles
 */
export const asyncRoutes = [

  {
    path: 'external-link',
    component: Layout,
    children: [
      {
        path: 'https://github.com/artGlory/glory-admin',
        meta: { title: 'External Link', icon: 'link' }
      }
    ]
  },

  {
    path: '/admin-manage',
    component: Layout,
    redirect: '/amdin-manage/admin-manage',
    name: 'BackendManage',
    meta: { title: '后台管理', icon: 'admin-fill', permissions: ['3c9ef7b90e5a43cc863941f0f2368021'] },
    children: [
      {
        path: '/amdin-manage/role-manage',
        name: 'RoleManage',
        component: () => import('@/views/adminRole/index'),
        meta: { title: '角色管理', permissions: ['226b8ab287ac479780b44f4ae1981848'] }
      },
      {
        path: '/amdin-manage/admin-manage',
        name: 'AdminManage',
        component: () => import('@/views/adminUser/index'),
        meta: { title: '用户管理', permissions: ['fa32475acdcf469ea6a58e2dc15ed40d'] }
      },
      {
        path: '/amdin-manage/admin-login-log',
        name: 'AdminLoginLog',
        component: () => import('@/views/adminLoginLog/index'),
        meta: { title: '用户登陆日志' }
      },
      {
        path: '/amdin-manage/admin-operate-log',
        name: 'AdminOperateLog',
        component: () => import('@/views/adminOperateLog/index'),
        meta: { title: '用户操作日志' }
      }
    ]
  },

  // 404 page must be placed at the end !!!
  { path: '*', redirect: '/404', hidden: true }
]

const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
