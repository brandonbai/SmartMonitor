/** When your routing table is too long, you can split it into small modules**/

import Layout from '@/views/layout/Layout'

const nestedRouter = {
  path: '/nested',
  component: Layout,
  redirect: '/nested/menu1',
  name: 'Nested',
  meta: {
    title: 'metaDataManage',
    icon: 'nested'
  },
  children: [
    {
      path: 'menu1',
      component: () => import('@/views/nested/menu1/index'), // Parent router-view
      name: 'Menu1',
      meta: { title: 'areaManage', icon: 'area' }
    },
    {
      path: 'menu2',
      component: () => import('@/views/nested/menu2/index'), // Parent router-view
      name: 'Menu2',
      meta: { title: 'nodeManage', icon: 'node' }
    },
    {
      path: 'menu3',
      component: () => import('@/views/nested/menu3/index'), // Parent router-view
      name: 'Menu3',
      meta: { title: 'sensorManage', icon: 'sensor' }
    },
    {
      path: 'menu4',
      name: 'Menu4',
      component: () => import('@/views/nested/menu4/index'),
      meta: { title: 'deviceManage', icon: 'device' }
    }
  ]
}

export default nestedRouter
