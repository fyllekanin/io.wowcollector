<script lang="ts" setup>
import { storeToRefs } from 'pinia';

import { useCharacterStore } from '@/stores';

const characterStore = useCharacterStore();

const { latestAchievements } = storeToRefs(characterStore);
</script>

<template>
  <v-container class="recent-achievements-container">
    <header>
      <h4 class="d-flex justify-self-left">Recently Aquired Achievements</h4>
    </header>
    <v-divider />
    <v-list class="pa-0" bg-color="foreground">
      <v-list-item
        class="pa-0 mt-3"
        v-for="(achievement, index) in latestAchievements"
        :key="index"
        :prepend-avatar="achievement.icon"
      >
        <v-list-item-title class="achievement-title">{{ achievement.name }}</v-list-item-title>
        <v-tooltip location="top" :text="achievement.description" open-delay="200">
          <template v-slot:activator="{ props }">
            <v-list-item-subtitle v-bind="props" class="achievement-description">{{
              achievement.description
            }}</v-list-item-subtitle>
          </template>
        </v-tooltip>
        <v-list-item-subtitle class="achievement-description">
          {{ new Date(achievement.completedAt).toISOString().split('T').join(' ').split('.')[0] }}
        </v-list-item-subtitle>
      </v-list-item>
    </v-list>
  </v-container>
</template>

<style lang="scss" scoped>
.recent-achievements-container {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.achievement-title,
.achievement-description {
  white-space: normal !important;
  word-break: break-word !important;
  text-align: left;
}
</style>
