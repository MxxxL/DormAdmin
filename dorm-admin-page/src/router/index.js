import Vue from 'vue'
import Router from 'vue-router'
import Home from '../views/Home.vue'
import Login from '../views/Login.vue'
import Profile from '../views/Profile.vue'

Vue.use(Router)

export default new Router({
  routes: [{
      path: '/',
      name: 'Login',
      component: Login,
      hidden: true
    },
    {
      path: '/home',
      name: 'Home',
      component: Home,
      hidden: true,
      meta: {
        roles: ['admin', 'user']
      },
      children: [{
        path: '/profile',
        name: 'Profile',
        component: Profile,
        hidden: true
      }]
    },
    {
      path: '*',
      redirect: '/home'
    }
  ]
})
