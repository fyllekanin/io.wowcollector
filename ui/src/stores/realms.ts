import { defineStore } from 'pinia';
import type { RealmInformation } from '@/types/realms';

export const useRealmStore = defineStore({
  id: 'realms',
  state: () => ({
    _realms: [] as RealmInformation[],
  }),
  getters: {
    realms(state) {
      return state._realms;
    },
  },
  actions: {
    save(realms: RealmInformation[]) {
      this.$state._realms = realms;
    },
  },
});
