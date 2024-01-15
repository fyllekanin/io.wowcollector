<script setup lang="ts">
import { ref, computed, watch, inject } from 'vue';
import { storeToRefs } from 'pinia';

import Toast from '@/components/Toast.vue';

import CharacterService from '@/service/CharacterService';

import { useRegionStore, useRealmStore, useTabStore } from '@/stores';
import { saveActiveCharacter } from '@/utils';

import type { CharacterInformation } from '@/types/character';

const isMobile = inject('$isMobile')!;

const emit = defineEmits(['search']);

const characterName = ref('');
const characterRealm = ref('');
const characterRegion = ref('eu');
const loading = ref(false);
const errorToast = ref();
const errorText = ref('An error occurred');

const realmStore = useRealmStore();
const regionStore = useRegionStore();
const tabStore = useTabStore();

const { regions } = storeToRefs(regionStore);

const searchCharacter = async () => {
  loading.value = true;
  const characterSearchParams: Partial<CharacterInformation> = {
    name: characterName.value,
    realm: characterRealm.value,
    region: characterRegion.value,
  };

  try {
    const character = await CharacterService.getCharacterInformation(characterSearchParams);
    if (!character) throw new Error('Character not found');

    saveActiveCharacter(
      { character },
      {
        saveToLocalStorage: true,
        saveToStore: true,
      },
    );

    tabStore.save('/');
    emit('search');
  } catch (error) {
    console.error(error);
    errorText.value = (error as any).message;
    errorToast.value.show();
  } finally {
    loading.value = false;
  }
};

const characterNameValid = computed(() => {
  return characterName.value?.length;
});
const allFilled = computed(() => {
  return characterNameValid.value && characterRealm.value && characterRegion.value;
});
const realms = computed(() => {
  return realmStore.realms
    .filter((realm) => realm.region === characterRegion.value)
    .sort((a, b) => {
      if (a.name < b.name) {
        return -1;
      }
      if (a.name > b.name) {
        return 1;
      }

      return 0;
    });
});

watch(characterRegion, (newVal) => {
  if (
    !realms.value.find((realm) => realm.region === newVal && realm.slug === characterRealm.value)
  ) {
    characterRealm.value = '';
  }
});
</script>

<template>
  <div :class="{ card: !isMobile }" class="container">
    <h3>Search for character</h3>
    <v-form class="card__content" @submit.prevent="searchCharacter">
      <v-text-field
        v-model="characterName"
        label="Character name"
        variant="underlined"
        class="character_name search__input"
        clearable
      />
      <v-autocomplete
        v-model="characterRealm"
        label="Character realm"
        variant="underlined"
        :items="realms || []"
        item-title="name"
        item-value="slug"
        class="character_realm search__input"
        clearable
      />
      <v-radio-group v-model="characterRegion" label="Region" color="primary" inline hide-details>
        <v-radio
          v-for="region in regions"
          :key="region"
          :label="region.toUpperCase()"
          :value="region"
        />
      </v-radio-group>
      <v-btn
        :disabled="!allFilled"
        color="primary"
        class="search__button"
        text="Search"
        :variant="!allFilled ? 'outlined' : 'flat'"
        type="submit"
        :loading="loading"
      />
    </v-form>
  </div>
  <Toast ref="errorToast" type="error" :text="errorText" />
</template>

<style scoped lang="scss" src="./SearchLayout.scss"></style>
