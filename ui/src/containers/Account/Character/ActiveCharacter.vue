<script lang="ts" setup>
import { computed } from 'vue';
import { storeToRefs } from 'pinia';

import { useCharacterStore } from '@/stores';
import { getRealmName } from '@/utils';
import { Icons } from '@/constants';

import { SpecializationsMap } from '@/constants/roles';

const characterStore = useCharacterStore();

const { activeCharacter, characters, defaultCharacter } = storeToRefs(characterStore);

const characterIsYours = computed(() =>
  characters.value.some((char) => char.id === activeCharacter.value?.id),
);

const clazz = computed(() =>
  characterIsYours.value
    ? (activeCharacter.value?.clazz?.toUpperCase()?.replace(/\s/g, '_') as keyof typeof Icons)
    : (defaultCharacter.value?.clazz?.toUpperCase()?.replace(/\s/g, '_') as keyof typeof Icons),
);
const spec = computed(() =>
  characterIsYours.value
    ? (SpecializationsMap[
        activeCharacter.value?.specialization as keyof typeof SpecializationsMap
      ] as keyof typeof Icons)
    : (SpecializationsMap[
        defaultCharacter.value?.specialization as keyof typeof SpecializationsMap
      ] as keyof typeof Icons),
);
const displayName = computed(() => {
  return (
    (characterIsYours.value ? activeCharacter.value?.name : defaultCharacter.value?.name) ?? ''
  );
});
const realmName = computed(() => {
  return (
    (characterIsYours.value
      ? getRealmName(activeCharacter.value?.realm as string)
      : getRealmName(defaultCharacter.value?.realm as string)) ?? ''
  );
});
const avatar = computed(() => {
  return (
    (characterIsYours.value
      ? activeCharacter.value?.media?.avatar
      : defaultCharacter.value?.media?.avatar) ?? ''
  );
});
</script>

<template>
  <v-container class="active-character-container">
    <header>
      <h1 v-if="!defaultCharacter">No character selected</h1>
      <div v-else class="active-character">
        <v-img
          class="flex-0-0"
          :src="avatar"
          :alt="defaultCharacter?.name"
          width="85px"
          height="85px"
        ></v-img>
        <div class="character-details">
          <div class="d-flex align-center">
            <h3>{{ displayName }}</h3>
          </div>
          <v-label>
            {{ realmName }}
          </v-label>
          <div class="icons">
            <v-tooltip location="bottom" :text="defaultCharacter?.clazz">
              <template v-slot:activator="{ props }">
                <v-icon v-bind="props" :icon="Icons[clazz]" size="32px" />
              </template>
            </v-tooltip>
            <v-tooltip location="bottom" :text="defaultCharacter?.specialization">
              <template v-slot:activator="{ props }">
                <v-icon v-bind="props" :icon="Icons[spec]" size="32px" />
              </template>
            </v-tooltip>
          </div>
        </div>
      </div>
    </header>
  </v-container>
</template>

<style lang="scss" scoped>
.active-character-container {
  max-width: 1200px !important;
  width: 100%;
  display: flex;
  flex-direction: column;
  gap: 1rem;
  padding-top: 0 !important;
  padding-bottom: 0 !important;

  @media (max-width: 768px) {
    flex-direction: column;
  }
}

.active-character {
  display: flex;
  gap: 1rem;
  backdrop-filter: blur(5px);
  background-color: rgba(0, 0, 0, 0.5);
  border-radius: 6px;

  .character-details {
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
  }

  .icons {
    display: flex;
    padding: 0 !important;
    margin: 0 !important;
  }
}

.achievement-container {
  display: flex;
  flex-direction: column;
  gap: 0.3rem;
  width: 100%;
  background-color: $foreground-color;
  padding: 1rem;
}
</style>
