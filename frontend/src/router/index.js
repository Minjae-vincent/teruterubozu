import { createRouter, createWebHistory } from 'vue-router';

import Home from '../views/HomePage.vue';
import Login from '../views/Login.vue';
import SignUp from '../views/SignUp.vue';

const routes = [
  {
    path: '/',
    name: 'Login',
    component: Login,
  },
  {
    path: '/sign-up',
    name: 'SignUp',
    component: SignUp,
  },
  {
    path: '/main',
    name: 'Home',
    component: Home,
    meta: { requiresAuth: true },
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

router.beforeEach((to, from, next) => {
  const accessToken = localStorage.getItem('accessToken');
  console.log(to.matched.some((record) => record.meta.requiresAuth));
  if (to.matched.some((record) => record.meta.requiresAuth)) {
    if (!accessToken) {
      next({ name: 'Login' });
    } else {
      next();
    }
  } else {
    next();
  }
});

export { router };
