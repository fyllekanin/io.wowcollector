import type { RouteRecordRaw } from 'vue-router';

import {
  before404,
  beforeLanding,
  beforeMount,
  beforeAchievements,
  beforeMe,
  beforeLogin,
} from '@/router/middlewares';

import LandingView from '@/views/LandingView.vue';
import MountView from '@/views/Collectables/MountView.vue';
import AchievementView from '@/views/Collectables/AchievementView.vue';
import SearchView from '@/views/SearchView.vue';
import AccountView from '@/views/AccountView.vue';
import NotFoundView from '@/views/NotFoundView.vue';

export default [
  {
    path: '/',
    name: 'landing',
    beforeEnter: beforeLanding,
    children: [
      {
        path: '',
        name: 'home',
        component: LandingView,
      },
      {
        path: '/mounts/:region?/:realm?/:name?',
        name: 'mounts',
        component: MountView,
        beforeEnter: beforeMount,
        meta: {
          isCollectable: true,
        },
      },
      {
        path: '/achievements/:region?/:realm?/:name?',
        name: 'achievements',
        component: AchievementView,
        beforeEnter: beforeAchievements,
        meta: {
          isCollectable: true,
        },
      },
      {
        path: '/search',
        name: 'search',
        component: SearchView,
      },
      {
        path: '/me',
        name: 'me',
        component: AccountView,
        beforeEnter: beforeMe,
        meta: {
          requiresAuth: true,
        },
      },
    ],
  },
  {
    path: '/login',
    name: 'login',
    beforeEnter: beforeLogin,
  },
  {
    path: '/:pathMatch(.*)*',
    name: '404',
    beforeEnter: before404,
    component: NotFoundView,
  },
] as RouteRecordRaw[];
