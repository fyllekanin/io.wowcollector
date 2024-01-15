import type { NavigationGuardNext, RouteLocationNormalized } from 'vue-router';
import type { CharacterInformation } from '@/types/character';

import { useCharacterStore, useLoadingStore, useCollectableStore, useTabStore } from '@/stores';
import { LOCAL_STORAGE } from '@/constants/storage';
import CharacterService from '@/service/CharacterService';
import CollectionService from '@/service/CollectionService';
import type { MountInformationResponse } from '@/types/collections';

export const beforeMount = async (
  to: RouteLocationNormalized,
  from: RouteLocationNormalized,
  next: NavigationGuardNext,
) => {
  const loadingStore = useLoadingStore();
  const characterStore = useCharacterStore();
  const tabStore = useTabStore();
  loadingStore.save(true);

  tabStore.save('/collectables');

  const { region, realm, name } = to.params;

  if (region && realm && name) {
    await cacheCharacter({
      region: region as string,
      realm: realm as string,
      name: name as string,
    });
    loadingStore.save(false);
    return next();
  }

  const character =
    localStorage.getItem(LOCAL_STORAGE.ACTIVE_CHARACTER) ?? characterStore.activeCharacter;
  if (!character) {
    loadingStore.save(false);
    tabStore.save('/search');
    return next('/search');
  }

  const characterParsed: CharacterInformation =
    typeof character === 'string' ? JSON.parse(character) : character;

  await cacheCharacter(characterParsed);
  next({
    name: 'mounts',
    params: {
      region: characterParsed.region,
      realm: characterParsed.realm,
      name: characterParsed.name,
    },
  });
  loadingStore.save(false);
};

const cacheCharacter = async (characterInformation: Partial<CharacterInformation>) => {
  const characterStore = useCharacterStore();
  const collectablesStore = useCollectableStore();
  const characterAlreadyCached = characterStore.activeCharacter?.name === characterInformation.name;
  const mountDataExists = collectablesStore.mountCategories.length;

  if (characterAlreadyCached && mountDataExists) return;

  const [character, mounts] = await Promise.all([
    CharacterService.getCharacterInformation(characterInformation),
    CollectionService.getCollectionInformation<MountInformationResponse>(
      characterInformation,
      'mounts',
    ),
  ]);
  if (!character || !mounts) return;

  const { mountCategories } = mounts;

  if (mountCategories) collectablesStore.saveMounts(mountCategories);
  if (character) characterStore.saveActiveCharacter(character);
};
