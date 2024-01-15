import { LOCAL_STORAGE } from '@/constants/storage';
import { useCollectableStore } from '@/stores';
import { useCharacterStore } from '@/stores/character';

import type { CharacterInformation } from '@/types/character';
import type { RequireAtLeastOne } from '@/types/generic';
import type { Achievement } from '@/types/wow';

export function clearCharacter(
  from: RequireAtLeastOne<{
    store: boolean;
    localStorage: boolean;
  }>,
) {
  if (from.localStorage) localStorage.removeItem(LOCAL_STORAGE.ACTIVE_CHARACTER);
  if (from.store) useCharacterStore().deleteActiveCharacter();
}

export function saveActiveCharacter(
  toSave: RequireAtLeastOne<{
    character: CharacterInformation;
    characters: CharacterInformation[];
  }>,
  options: RequireAtLeastOne<{
    saveToLocalStorage: boolean;
    saveToStore: boolean;
  }>,
) {
  if (options.saveToLocalStorage && toSave.character) {
    localStorage.setItem(LOCAL_STORAGE.ACTIVE_CHARACTER, JSON.stringify(toSave.character));
  }

  if (!characterBelongsToUser(toSave.character)) {
    useCollectableStore().clearAll();
  }

  if (options.saveToStore) {
    if (toSave.character) useCharacterStore().saveActiveCharacter(toSave.character);
    if (toSave.characters) useCharacterStore().saveMultipleCharacters(toSave.characters);
  }
}

export function saveDefaultCharacter(
  toSave: RequireAtLeastOne<{
    character: CharacterInformation;
    characters: CharacterInformation[];
  }>,
  options: RequireAtLeastOne<{
    saveToLocalStorage: boolean;
    saveToStore: boolean;
  }>,
) {
  if (options.saveToLocalStorage && toSave.character) {
    localStorage.setItem(LOCAL_STORAGE.DEFAULT_CHARACTER, JSON.stringify(toSave.character));
  }

  if (options.saveToStore) {
    if (toSave.character) useCharacterStore().saveDefaultCharacter(toSave.character);
    if (toSave.characters) useCharacterStore().saveMultipleCharacters(toSave.characters);
  }
}

export async function saveMe(
  toSave: RequireAtLeastOne<{
    characters: CharacterInformation[];
    achievements: Achievement[];
    latestAchievements: Achievement[];
  }>,
  options: RequireAtLeastOne<{
    saveToLocalStorage: boolean;
    saveToStore: boolean;
  }>,
) {
  if (options.saveToLocalStorage) {
    if (toSave.characters)
      localStorage.setItem(LOCAL_STORAGE.CHARACTERS, JSON.stringify(toSave.characters));
    if (toSave.achievements)
      localStorage.setItem(LOCAL_STORAGE.ACHIEVEMENTS, JSON.stringify(toSave.achievements));
    if (toSave.latestAchievements)
      localStorage.setItem(
        LOCAL_STORAGE.LATEST_ACHIEVEMENTS,
        JSON.stringify(toSave.latestAchievements),
      );
  }

  if (options.saveToStore) {
    if (toSave.characters) useCharacterStore().saveMultipleCharacters(toSave.characters);
    if (toSave.achievements) useCharacterStore().saveAchievements(toSave.achievements);
    if (toSave.latestAchievements)
      useCharacterStore().saveLatestAchievements(toSave.latestAchievements);
  }
}

export function characterBelongsToUser(character?: CharacterInformation) {
  const characterStore = useCharacterStore();

  const prevCharBlongsToUser = characterStore.characters.some(
    (c) => c.id === character?.id || c.name === character?.name,
  );
  const nextCharBlongsToUser = characterStore.characters.some(
    (c) =>
      c.id === characterStore.activeCharacter?.id ||
      c.name === characterStore.activeCharacter?.name,
  );

  return prevCharBlongsToUser && nextCharBlongsToUser;
}
