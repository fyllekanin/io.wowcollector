import { defineStore } from 'pinia';

export const useLoadingStore = defineStore({
  id: 'loading',
  state: () => ({
    _loading: true,
  }),
  getters: {
    loading(state) {
      return state._loading;
    },
  },
  actions: {
    save(loading: boolean) {
      this.$state._loading = loading;
    },
  },
});
