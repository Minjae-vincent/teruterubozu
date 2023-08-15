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
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export { router };
