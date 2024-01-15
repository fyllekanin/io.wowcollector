<script setup lang="ts">
import { onMounted } from 'vue';

import Header from '@/components/Header.vue';
import AchievementLayout from '@/layouts/Collectables/Achievement/AchievementLayout.vue';

import router from '@/router';

import CharacterService from '@/service/CharacterService';
import { saveActiveCharacter } from '@/utils';

onMounted(async () => {
  const { region, realm, name } = router.currentRoute.value.params;

  if (!region || !realm || !name) {
    return;
  }

  const character = await CharacterService.getCharacterInformation({
    region: region as string,
    realm: realm as string,
    name: name as string,
  });

  if (!character) return;

  saveActiveCharacter(
    {
      character,
    },
    {
      saveToStore: true,
      saveToLocalStorage: true,
    },
  );
});
</script>

<template>
  <div class="center-container">
    <Header title="Achievements" />
    <AchievementLayout />
  </div>
</template>

<style scoped lang="scss">
.center-container {
  @media (min-width: 640px) and (max-width: 1240px) {
    padding: 0 1rem 0 1rem !important;
  }

  @media (max-width: 600px) {
    margin-top: 5rem;
  }

  margin-top: 2rem;
  display: grid;
  place-items: center;
}
</style>
