<script setup lang="ts">
import { onMounted } from 'vue';

import Header from '@/components/Header.vue';
import MountLayout from '@/layouts/Collectables/Mount/MountLayout.vue';

import router from '@/router';

import CharacterService from '@/service/CharacterService';
import { saveActiveCharacter } from '@/utils';
import { useCollectableStore } from '@/stores';
import CollectionService from '@/service/CollectionService';

import type { MountInformationResponse } from '@/types/collections';

onMounted(async () => {
  const { region, realm, name } = router.currentRoute.value.params;

  if (!region || !realm || !name) {
    return;
  }

  const characterPayload = {
    region: region as string,
    realm: realm as string,
    name: name as string,
  };

  const [character, mounts] = await Promise.all([
    CharacterService.getCharacterInformation(characterPayload),
    CollectionService.getCollectionInformation<MountInformationResponse>(
      characterPayload,
      'mounts',
    ),
  ]);

  if (!character || !mounts) return;

  saveActiveCharacter(
    {
      character,
    },
    {
      saveToStore: true,
      saveToLocalStorage: true,
    },
  );
  useCollectableStore().saveMounts(mounts.mountCategories);
});
</script>

<template>
  <div class="center-container">
    <Header title="Mounts" />
    <MountLayout />
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
