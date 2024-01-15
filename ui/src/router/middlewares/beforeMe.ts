import type { NavigationGuardNext, RouteLocationNormalized } from 'vue-router';

import { useAuthStore } from '@/stores/auth';
import { useCharacterStore, useLoadingStore } from '@/stores';
import CharacterService from '@/service/CharacterService';
import { saveMe } from '@/utils';
import { LOCAL_STORAGE } from '@/constants/storage';

export const beforeMe = async (
  to: RouteLocationNormalized,
  from: RouteLocationNormalized,
  next: NavigationGuardNext,
) => {
  const loadingStore = useLoadingStore();
  const authStore = useAuthStore();
  const characterStore = useCharacterStore();
  loadingStore.save(true);

  if (!authStore.isAuthenticated) {
    next({ name: 'home' });
    loadingStore.save(false);
    return;
  }

  if (!characterStore.characters.length && !localStorage.getItem(LOCAL_STORAGE.CHARACTERS)) {
    const me = await CharacterService.me();
    if (!me) return next({ name: 'home' }); // show error message

    await saveMe(me, {
      saveToLocalStorage: true,
      saveToStore: true,
    });
  } else {
    const characters = localStorage.getItem(LOCAL_STORAGE.CHARACTERS);
    const achievements = localStorage.getItem(LOCAL_STORAGE.ACHIEVEMENTS);
    const latestAchievements = localStorage.getItem(LOCAL_STORAGE.LATEST_ACHIEVEMENTS);

    if (characters) characterStore.saveMultipleCharacters(JSON.parse(characters));
    if (achievements) characterStore.saveAchievements(JSON.parse(achievements));
    if (latestAchievements) characterStore.saveLatestAchievements(JSON.parse(latestAchievements));
  }

  loadingStore.save(false);
  next();
};
