import { defineStore } from 'pinia';

export const useTabStore = defineStore({
  id: 'tab',
  state: () => ({
    _tab: '/' as string | null,
  }),
  getters: {
    activeTab(state) {
      return state._tab;
    },
  },
  actions: {
    save(tab: string | null) {
      this.$state._tab = tab;
    },
  },
});
