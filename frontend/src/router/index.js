import { createRouter, createWebHistory } from 'vue-router';

import Home from '../views/HomePage.vue';
import Login from '../views/Login.vue';

const routes = [
  {
    path: '/',
    name: 'Login',
    component: Login,
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
