import { useAuthStore } from '@/stores/auth';
import { useCharacterStore } from '@/stores';

import type { AuthenticatedUser } from '@/types/auth';
import type { RequireAtLeastOne } from '@/types/generic';

import { goTo } from '.';
import { LOCAL_STORAGE } from '@/constants/storage';

export function saveAuthentication(
  auth: AuthenticatedUser,
  options: RequireAtLeastOne<{
    saveToLocalStorage: boolean;
    saveToStore: boolean;
  }>,
) {
  if (options.saveToLocalStorage) {
    localStorage.setItem(
      LOCAL_STORAGE.AUTH,
      JSON.stringify({
        ...auth,
      }),
    );
  }

  if (options.saveToStore) {
    useAuthStore().save({
      ...auth,
    });
  }
}

export function login() {
  window.location.href = '/api/v1/auth/redirect';
}

export function logout() {
  useAuthStore().delete();
  useCharacterStore().clearAll();
  clearAllLocalStore();
  goTo('/');
}

export function clearAllLocalStore() {
  localStorage.removeItem(LOCAL_STORAGE.AUTH);
  localStorage.removeItem(LOCAL_STORAGE.ACTIVE_CHARACTER);
  localStorage.removeItem(LOCAL_STORAGE.CHARACTERS);
  localStorage.removeItem(LOCAL_STORAGE.DEFAULT_CHARACTER);
  localStorage.removeItem(LOCAL_STORAGE.LATEST_ACHIEVEMENTS);
}
