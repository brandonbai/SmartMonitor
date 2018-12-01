/** When your routing table is too long, you can split it into small modules**/

import Layout from '@/views/layout/Layout'

const chartsRouter = {
  path: '/charts',
  component: Layout,
  redirect: 'noredirect',
  name: 'Charts',
  meta: {
    title: 'dataCharts',
    icon: 'chart'
  },
  children: [
    {
      path: 'overview',
      component: () => import('@/views/charts/line'),
      name: 'Overview',
      meta: { title: 'Overview', noCache: true }
    },
    {
      path: 'detail',
      component: () => import('@/views/charts/mixChart'),
      name: 'Detail',
      meta: { title: 'Detail', noCache: true }
    }
  ]
}

export default chartsRouter
