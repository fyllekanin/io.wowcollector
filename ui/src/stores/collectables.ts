import { defineStore } from 'pinia';
import type { MountCategory } from '@/types/collections/mounts';
import type { AchievementCategory } from '@/types/collections/achievements';

export const useCollectableStore = defineStore({
  id: 'collectables',
  state: () => ({
    _mountCategories: [] as MountCategory[],
    _achievementCategories: [] as AchievementCategory[],
  }),
  getters: {
    mountCategories(state) {
      return state._mountCategories;
    },
    achievementCategories(state) {
      return state._achievementCategories;
    },
  },
  actions: {
    // Set
    saveMounts(categories: MountCategory[]) {
      this.$state._mountCategories = categories;
    },
    saveAchievements(categories: AchievementCategory[]) {
      this.$state._achievementCategories = categories;
    },

    // Delete
    deleteMounts() {
      this.$state._mountCategories = [];
    },
    deleteAchievements() {
      this.$state._achievementCategories = [];
    },
    clearAll() {
      this.$state._mountCategories = [];
      this.$state._achievementCategories = [];
    },
  },
});
