import { defineStore } from 'pinia';

import type { AuthenticatedUser } from '@/types/auth';

export const useAuthStore = defineStore({
  id: 'auth',
  state: () => ({
    _user: null as AuthenticatedUser | null,
  }),
  getters: {
    isAuthenticated(state) {
      return !!state._user;
    },
    user(state) {
      return state._user;
    },
  },
  actions: {
    save(user: AuthenticatedUser) {
      this.$state._user = user;
    },
    delete() {
      this.$state._user = null;
    },
  },
});
