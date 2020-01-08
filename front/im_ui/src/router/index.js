import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../views/Login.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Login',
    component: Login
  },
  {
    path: '/main',
    name: 'main',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/Main.vue'),
    children: [

      {
        path: 'Friends',
        component: () => import(/* webpackChunkName: "about" */ '../views/Friends.vue'),
      },
      {
        path: 'Room',
        component: () => import(/* webpackChunkName: "about" */ '../views/Room.vue'),
      }
    ]
  }, {
    path: '/Chat',
    name: 'Chat',
    component: () => import(/* webpackChunkName: "about" */ '../views/Chat.vue'),
  }

]

const router = new VueRouter({
  routes
})

export default router
