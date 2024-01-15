<script lang="ts" setup>
import { ref, watch, inject } from 'vue';
import { storeToRefs } from 'pinia';

import { useAuthStore, useCharacterStore } from '@/stores';

import {
  getRealmName,
  saveActiveCharacter,
  saveAuthentication,
  saveDefaultCharacter,
} from '@/utils';
import { Icons } from '@/constants';

import type { CharacterInformation } from '@/types/character';
import CharacterService from '@/service/CharacterService';
import type { AuthenticatedUser } from '@/types/auth';

const isSmallTablet = inject<boolean>('$isSmallTablet');

const characterStore = useCharacterStore();
const authStore = useAuthStore();

const { characters, activeCharacter, defaultCharacter } = storeToRefs(characterStore);
const { user } = storeToRefs(authStore);
const selectedCharacter = ref(
  characters.value.findIndex((char) => char.id === activeCharacter.value?.id),
);
const search = ref('');
const characterListScrollerRef = ref();
const characterListSliderRef = ref();

const isSelectedCharacter = (char: CharacterInformation) => {
  return char.id === activeCharacter.value?.id;
};
const isDefaultCharacter = (char: CharacterInformation) => {
  return char.id === defaultCharacter?.value?.id;
};
const findIndexOfCharacter = (char: CharacterInformation) => {
  return characters.value.findIndex((c) => c.id === char.id);
};

const select = (index: number) => {
  selectedCharacter.value = index;
  characterListScrollerRef.value.scrollToIndex(index);
  characterListSliderRef.value.scrollTo('next');
};

const setDefault = async (char: CharacterInformation) => {
  const response = await CharacterService.updateDefaultCharacter(char.id);
  if (!response) return;

  const newDefault = characterStore.characters.find((c) => c.id === char.id);
  if (!newDefault) return;

  saveDefaultCharacter(
    {
      character: newDefault,
    },
    {
      saveToLocalStorage: true,
      saveToStore: true,
    },
  );
  saveAuthentication(
    {
      ...(user.value as AuthenticatedUser),
      defaultCharacter: newDefault,
    },
    {
      saveToLocalStorage: true,
      saveToStore: true,
    },
  );
};

watch(selectedCharacter, (newVal) => {
  if (newVal !== -1) {
    saveActiveCharacter(
      {
        character: characters.value[newVal],
      },
      {
        saveToLocalStorage: true,
        saveToStore: true,
      },
    );
  }
});
</script>

<template>
  <header>
    <h2 class="d-flex justify-self-left">Character Select</h2>
    <v-label class="character-annotaion d-flex justify-self-left"
      >Some characters may not show if they have been inactive for a longer period*</v-label
    >
  </header>
  <v-container class="character-select-container">
    <v-card
      class="character-select-left"
      rounded
      elevation="10"
      :min-width="isSmallTablet ? '250' : '300'"
      color="foreground"
    >
      <v-text-field
        v-model="search"
        label="Search for a character"
        outlined
        dense
        hide-details
      ></v-text-field>
      <v-virtual-scroll
        class="character-list"
        :items="characters.filter((char) => char.name.toLowerCase().includes(search.toLowerCase()))"
        height="200"
        item-height="48"
        ref="characterListScrollerRef"
      >
        <template v-slot:default="{ item }">
          <v-list-item :title="item.name" :subtitle="getRealmName(item.realm)">
            <template v-slot:prepend>
              <v-avatar>
                <v-img v-if="item?.media?.avatar" :src="item.media.avatar" />
                <v-icon v-else size="x-large" :icon="Icons.QUESTION_MARK"></v-icon>
              </v-avatar>
            </template>

            <template v-slot:append>
              <v-btn
                v-if="isSelectedCharacter(item)"
                size="x-small"
                variant="tonal"
                color="primary"
              >
                Selected</v-btn
              >
              <v-btn
                v-else
                size="x-small"
                variant="tonal"
                @click="select(findIndexOfCharacter(item))"
                >Select</v-btn
              >
              <v-tooltip
                :text="isDefaultCharacter(item) ? 'Default' : 'Set as default'"
                location="top"
              >
                <template v-slot:activator="{ props }">
                  <v-icon
                    v-if="isDefaultCharacter(item)"
                    v-bind="props"
                    color="primary"
                    :icon="Icons.STAR"
                    size="small"
                  />
                  <v-icon
                    v-else
                    class="default-character-icon"
                    v-bind="props"
                    color="white"
                    :icon="Icons.STAR_OUTLINE"
                    size="small"
                    @click="setDefault(item)"
                  />
                </template>
              </v-tooltip>
            </template>
          </v-list-item>
        </template>
      </v-virtual-scroll>
    </v-card>
    <v-card
      v-if="!isSmallTablet"
      class="character-select-right"
      rounded
      elevation="10"
      max-width="800"
      color="foreground"
    >
      <v-slide-group
        ref="characterListSliderRef"
        v-model="selectedCharacter"
        class="pa-4"
        show-arrows
        mandatory
      >
        <v-slide-group-item
          v-for="(character, index) in characters"
          :key="index"
          v-slot="{ toggle, selectedClass }"
        >
          <v-card
            :class="['ma-4', selectedClass, 'd-flex flex-column justify-center align-center']"
            height="200"
            width="100"
            @click="
              () => {
                toggle();
                select(index);
              }
            "
            elevation="10"
            :color="index === selectedCharacter ? 'foregroundLight' : 'foreground'"
          >
            <v-img
              v-if="character?.media?.mainRaw"
              :src="character?.media?.mainRaw"
              class="character-avatar"
              alt="Avatar"
              height="200"
              width="100"
            />
            <v-icon v-else :icon="Icons.QUESTION_MARK" size="x-large"></v-icon>
          </v-card>
        </v-slide-group-item>
      </v-slide-group>
    </v-card>
  </v-container>
</template>

<style lang="scss" scoped>
h2,
.character-annotaion {
  padding-left: 1rem;
}

.character-annotaion {
  font-size: $font-x-small;
  width: 300px;
  white-space: normal;
  word-break: break-word;
  text-align: left;
  font-style: italic;
}

.character-select-container {
  max-width: 1200px !important;
  width: 100%;
  display: flex;
  flex-direction: row;
  justify-content: center;
  gap: 1rem;

  @media (max-width: 1200px) {
    flex-direction: column;
  }
}

.character-list {
  scroll-behavior: smooth;
}

.character-select-left {
  min-width: 300px;
  @media (max-width: 768px) {
    flex: 1 1;
  }
}

.character-select-right {
  flex: 1 1;
  min-width: 400px;
}

.banner {
  position: relative;
  transform: scale(4);
}

.character-avatar {
  transform: scale(4) translateY(-2px) !important;
}

.character-name {
  position: absolute;
}

.default-character-icon {
  cursor: pointer;
}
</style>
