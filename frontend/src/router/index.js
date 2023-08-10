import { createRouter, createWebHistory } from 'vue-router';

import Home from '../views/HomePage.vue';
import Test from '../views/Test.vue';

const routes = [
  {
    path: '/',
    name: 'Test',
    component: Test,
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
