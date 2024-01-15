import type { NavigationGuardNext, RouteLocationNormalized } from 'vue-router';
import type { CharacterInformation } from '@/types/character';

import RealmService from '@/service/RealmService';

import {
  useCharacterStore,
  useLoadingStore,
  useRealmStore,
  useRegionStore,
  useTabStore,
} from '@/stores';

import { clearCharacter, saveActiveCharacter, saveDefaultCharacter } from '@/utils';
import { useAuthStore } from '@/stores/auth';
import CharacterService from '@/service/CharacterService';
import { LOCAL_STORAGE } from '@/constants';

export const beforeLanding = async (
  to: RouteLocationNormalized,
  from: RouteLocationNormalized,
  next: NavigationGuardNext,
) => {
  const tabStore = useTabStore();
  const loadingStore = useLoadingStore();
  const authStore = useAuthStore();
  loadingStore.save(true);

  const auth = localStorage.getItem(LOCAL_STORAGE.AUTH);
  if (auth) {
    const authParsed = JSON.parse(auth);
    authStore.save(authParsed);
  }

  await Promise.all([populateRealmData(), populateCharacterData(to)]);

  if (to.name === 'home') tabStore.save('/');
  else if (!to.meta.isCollectable) tabStore.save(`/${to.name as string}`);
  else tabStore.save('/collectables');

  loadingStore.save(false);

  next();
};

// const init = async () => {
//   const authStore = useAuthStore();
//   const auth = localStorage.getItem(LOCAL_STORAGE.AUTH);
//   if (!auth) return;

//   const authParsed = JSON.parse(auth);
//   const { accessToken, refreshToken } = authParsed;

//   if (!accessToken || !refreshToken) return;

//   const response = await CharacterService.renewToken({ accessToken, refreshToken });
//   if (!response) return;

//   const { accessToken: newAccessToken, refreshToken: newRefreshToken } = response;
//   if (!newAccessToken || !newRefreshToken) return;

//   authStore.save({
//     accessToken: newAccessToken,
//     refreshToken: newRefreshToken,
//   });
// };

const populateRealmData = async () => {
  const realmStore = useRealmStore();
  const regionStore = useRegionStore();

  if (realmStore.realms?.length) return;

  const response = await RealmService.getRealms();
  if (!response) return;

  const { realms, regions } = response;
  if (realms) realmStore.save(realms);
  if (regions) regionStore.save(regions);
};

const populateCharacterData = async (to: RouteLocationNormalized) => {
  const params = to.params || {};
  const characterSearchParams: Partial<CharacterInformation> = {};
  const characterStore = useCharacterStore();

  const activeCharacter = localStorage.getItem(LOCAL_STORAGE.ACTIVE_CHARACTER);
  const defaultCharacter = localStorage.getItem(LOCAL_STORAGE.DEFAULT_CHARACTER);
  const authData = localStorage.getItem(LOCAL_STORAGE.AUTH);
  const latestAchievements = localStorage.getItem(LOCAL_STORAGE.LATEST_ACHIEVEMENTS);

  if (params.region && params.realm && params.name) {
    Object.assign(characterSearchParams, {
      region: params.region as string,
      realm: params.realm as string,
      name: params.name as string,
    });
  } else if (activeCharacter) {
    Object.assign(characterSearchParams, JSON.parse(activeCharacter));
  } else if (authData) {
    const parsedAuth = JSON.parse(authData);
    if (parsedAuth.defaultCharacter) {
      Object.assign(characterSearchParams, parsedAuth.defaultCharacter);
    }
  }

  if (Object.keys(characterSearchParams).length === 0) {
    return;
  }

  const character = await CharacterService.getCharacterInformation(characterSearchParams);
  if (!character) {
    clearCharacter({
      store: true,
      localStorage: true,
    });
    if (authData)
      saveDefaultCharacter(
        {
          character: JSON.parse(authData).defaultCharacter,
        },
        {
          saveToLocalStorage: true,
          saveToStore: true,
        },
      );
    return;
  }

  saveActiveCharacter(
    { character },
    {
      saveToLocalStorage: true,
      saveToStore: true,
    },
  );
  if (defaultCharacter) {
    saveDefaultCharacter(
      {
        character: JSON.parse(defaultCharacter),
      },
      {
        saveToLocalStorage: true,
        saveToStore: true,
      },
    );
  }
  if (latestAchievements) {
    characterStore.saveLatestAchievements(JSON.parse(latestAchievements));
  }
};
