<script lang="ts" setup>
import { ref, computed, inject } from 'vue';
import { storeToRefs } from 'pinia';

import CharacterSelect from '@/containers/Account/Character/CharacterSelect.vue';
import ActiveCharacter from '@/containers/Account/Character/ActiveCharacter.vue';
import RecentAchievements from '@/containers/Account/Character/RecentAchievements.vue';
import Preferences from '@/containers/Account/Preferences/Preferences.vue';

import { useCharacterStore } from '@/stores';
import { Icons } from '@/constants';

const isMobile = inject('$isMobile');

const characterStore = useCharacterStore();

const { activeCharacter, defaultCharacter } = storeToRefs(characterStore);
const activeTab = ref(1);
const faction = computed(
  () =>
    activeCharacter.value?.faction?.toLowerCase() || defaultCharacter.value?.faction?.toLowerCase(),
);
</script>

<template>
  <v-container class="container">
    <v-tabs
      v-model="activeTab"
      color="primary"
      align-tabs="start"
      show-arrows
      stacked
      :grow="!!isMobile"
      :height="isMobile ? '48px' : ''"
    >
      <v-tab :value="1">
        <v-icon :icon="Icons.ACCOUNT_GROUP" />
        <label v-if="!isMobile">Characters</label>
      </v-tab>
      <v-tab :value="2">
        <v-icon :icon="Icons.PREFERENCES" />
        <label v-if="!isMobile">Preferences</label>
      </v-tab>
    </v-tabs>
    <v-window v-model="activeTab">
      <v-window-item :value="1" class="character-content">
        <v-card
          :class="['pa-3', 'active-character-card', faction]"
          color="foreground"
          elevation="10"
        >
          <ActiveCharacter />
        </v-card>
        <v-card class="pa-3 character-select-card" color="foreground" elevation="10">
          <CharacterSelect />
        </v-card>
        <v-card class="pa-3 recent-achievements-card" color="foreground" elevation="10">
          <RecentAchievements />
        </v-card>
      </v-window-item>
      <v-window-item :value="2" class="preferences-content">
        <v-card class="pa-3 mt-4 preferences" color="foreground" elevation="10">
          <Preferences />
        </v-card>
      </v-window-item>
    </v-window>
  </v-container>
</template>

<style lang="scss" scoped src="./AccountLayout.scss"></style>
