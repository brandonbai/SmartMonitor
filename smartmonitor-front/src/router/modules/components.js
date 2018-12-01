/** When your routing table is too long, you can split it into small modules**/

import Layout from '@/views/layout/Layout'

const componentsRouter = {
  path: '/components',
  component: Layout,
  redirect: 'noredirect',
  name: 'ComponentDemo',
  meta: {
    title: 'Message',
    icon: 'message'
  },
  children: [
    {
      path: 'mixin',
      component: () => import('@/views/components-demo/mixin'),
      name: 'ComponentMixinDemo',
      meta: { title: 'componentMixin' }
    },
    {
      path: 'back-to-top',
      component: () => import('@/views/components-demo/backToTop'),
      name: 'BackToTopDemo',
      meta: { title: 'backToTop' }
    },
    {
      path: 'drag-dialog',
      component: () => import('@/views/components-demo/dragDialog'),
      name: 'DragDialogDemo',
      meta: { title: 'dragDialog' }
    }
  ]
}

export default componentsRouter
