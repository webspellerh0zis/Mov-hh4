import * as VueRouter from 'vue-router'
import FormEditorView from './views/formEditor.vue'
import FormEditorObjListView from './views/formEditor/objList.vue'
import FormEditorObjEditView from './views/formEditor/objEdit.vue'
const routes = [
  {
    path: '/',
    redirect: '/formEditor'
  },
  {
    path: '/formEditor',
    component: FormEditorView
  },
  {
    path: '/formEditor/object',
    component: {
      template: '<router-view></router-view>'
    },
    children: [
      {
        name: 'objList',
        path: 'objList',
        component: FormEditorObjListView
      },
      {
        name: 'objEdit',
        path: 'objEdit/:objid?',
        component: FormEditorObjEditView
      }
    ]
  }
]
export default VueRouter.createRouter({
  history: VueRouter.createWebHistory(),
  routes
})
