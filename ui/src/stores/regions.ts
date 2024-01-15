import { defineStore } from 'pinia';
import type { Region } from '@/types/realms';

export const useRegionStore = defineStore({
  id: 'regions',
  state: () => ({
    _regions: [] as Region[],
  }),
  getters: {
    regions(state) {
      return state._regions;
    },
  },
  actions: {
    save(regions: Region[]) {
      this.$state._regions = regions;
    },
  },
});
