import type { NavigationGuardNext, RouteLocationNormalized } from 'vue-router';

import { useLoadingStore } from '@/stores';
import AuthService from '@/service/AuthService';
import { saveAuthentication, saveActiveCharacter, saveDefaultCharacter } from '@/utils';
import { LOCAL_STORAGE } from '@/constants/storage';

export const beforeLogin = async (
  to: RouteLocationNormalized,
  from: RouteLocationNormalized,
  next: NavigationGuardNext,
) => {
  const loadingStore = useLoadingStore();
  loadingStore.save(true);

  const { code } = to.query;
  if (!code) return next('/');

  const response = await AuthService.login(code as string);
  if (!response) return next('/');

  saveAuthentication(response, {
    saveToLocalStorage: true,
    saveToStore: true,
  });
  if (response.defaultCharacter) {
    // const character = await CharacterService.getCharacterInformation(
    //   response.defaultCharacter,
    // );
    // if (characterResponse) {
    //   const collectablesStore = useCollectableStore();
    //   collectablesStore.saveMounts(characterResponse.mountCategories);
    // }
    saveDefaultCharacter(
      {
        character: response.defaultCharacter,
      },
      {
        saveToLocalStorage: true,
        saveToStore: true,
      },
    );

    if (!localStorage.getItem(LOCAL_STORAGE.ACTIVE_CHARACTER)) {
      saveActiveCharacter(
        {
          character: response.defaultCharacter,
        },
        {
          saveToLocalStorage: true,
          saveToStore: true,
        },
      );
    }
  }

  next('/');
  loadingStore.save(false);
};
