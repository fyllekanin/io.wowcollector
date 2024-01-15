import { defineStore } from 'pinia';
import type { CharacterInformation } from '@/types/character';
import type { Achievement } from '@/types/wow';

export const useCharacterStore = defineStore({
  id: 'character',
  state: () => ({
    _activeCharacter: null as CharacterInformation | null,
    _defaultCharacter: null as CharacterInformation | null,
    _characters: [] as CharacterInformation[],
    _achievements: [] as Achievement[],
    _latestAchievements: [] as Achievement[],
  }),
  getters: {
    activeCharacter(state) {
      return state._activeCharacter;
    },
    defaultCharacter(state) {
      return state._defaultCharacter;
    },
    characters(state) {
      return state._characters;
    },
    achievements(state) {
      return state._achievements;
    },
    latestAchievements(state) {
      return state._latestAchievements;
    },
  },
  actions: {
    saveActiveCharacter(character: CharacterInformation) {
      this.$state._activeCharacter = character;
    },
    saveDefaultCharacter(character: CharacterInformation) {
      this.$state._defaultCharacter = character;
    },
    saveMultipleCharacters(characters: CharacterInformation[]) {
      this.$state._characters = characters.sort((a, b) => b.level - a.level);
    },
    deleteActiveCharacter() {
      this.$state._activeCharacter = null;
    },
    deleteDefaultCharacter() {
      this.$state._defaultCharacter = null;
    },
    saveAchievements(achievements: Achievement[]) {
      this.$state._achievements = achievements;
    },
    saveLatestAchievements(achievements: Achievement[]) {
      this.$state._latestAchievements = achievements;
    },
    deleteAchievements() {
      this.$state._achievements = [];
    },
    deleteLatestAchievements() {
      this.$state._latestAchievements = [];
    },
    clearAll() {
      this.$state._activeCharacter = null;
      this.$state._defaultCharacter = null;
      this.$state._characters = [];
      this.$state._achievements = [];
      this.$state._latestAchievements = [];
    },
  },
});
