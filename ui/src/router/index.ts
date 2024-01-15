import { createRouter, createWebHistory } from 'vue-router';
import { nextTick } from 'vue';

import Routes from './routes';

import { useCharacterStore, useAuthStore } from '@/stores';

import { upperCaseFirst } from '@/utils';
import { LOCAL_STORAGE } from '@/constants/storage';

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: Routes,
  scrollBehavior(to, _, savedPosition) {
    if (to.hash) return { el: to.hash };
    if (savedPosition) return savedPosition;
    return { top: 0, left: 0 };
  },
});

router.beforeEach(async (to, _, next) => {
  const auth = localStorage.getItem(LOCAL_STORAGE.AUTH);
  const authStore = useAuthStore();
  if (!auth && authStore.isAuthenticated) {
    authStore.delete();
  }

  if (to.meta.requiresAuth && !auth) {
    return next({ name: 'home' });
  }

  next();
});

router.afterEach(async (to) => {
  const characterStore = useCharacterStore();
  await nextTick();
  if (characterStore.activeCharacter?.name) {
    document.title = `${characterStore.activeCharacter.name} - WoW Collector Beta`;
  } else if (to.name) {
    document.title = `${upperCaseFirst(to.name.toString())} - WoW Collector Beta`;
  } else {
    document.title = 'WoW Collector Beta';
  }
});

export default router;
