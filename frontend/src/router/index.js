import { createRouter, createWebHistory } from 'vue-router';

import Home from '../components/HomePage.vue';
import Test from '../components/Test.vue';

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home,
  },
  {
    path: '/test',
    name: 'Test',
    component: Test,
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export { router };
