<script lang="ts" setup>
import { storeToRefs } from 'pinia';

import { useAuthStore } from '@/stores/auth';
import { useCharacterStore } from '@/stores';

import { getRealmName } from '@/utils';
import { Icons } from '@/constants';
import { computed } from 'vue';

const authStore = useAuthStore();
const characterStore = useCharacterStore();

const { isAuthenticated, user } = storeToRefs(authStore);
const { activeCharacter } = storeToRefs(characterStore);

const avatar = computed(() => activeCharacter.value?.media?.avatar ?? '');

defineEmits(['new-search', 'log-out']);
</script>

<template>
  <v-menu min-width="200px" rounded>
    <template v-slot:activator="{ props }">
      <v-btn icon v-bind="props" width="48px" height="48px">
        <v-avatar>
          <v-img v-if="activeCharacter?.media?.avatar" :src="avatar" alt="Avatar"></v-img>
          <v-icon v-else size="x-large" :icon="Icons.ACCOUNT_CIRCLE"></v-icon>
        </v-avatar>
      </v-btn>
    </template>
    <v-card color="foreground">
      <v-card-text class="pa-0">
        <header class="card-header">
          <div class="btag-container" v-if="user?.battleTag">
            <v-icon :icon="Icons.BATTLENET" />
            <div class="d-flex">
              <h3 class="btag-name">
                {{ user.battleTag.split('#')[0] }}
              </h3>
              <h3 class="diciminator">#{{ user.battleTag.split('#')[1] }}</h3>
            </div>
          </div>
          <v-divider v-if="isAuthenticated" />
          <div class="active-character" v-if="activeCharacter">
            <v-avatar>
              <v-img v-if="activeCharacter?.media?.avatar" :src="avatar" alt="Avatar"></v-img>
              <v-icon v-else size="x-large" :icon="Icons.ACCOUNT_CIRCLE"></v-icon>
            </v-avatar>
            <div class="d-flex flex-column">
              <h3>{{ activeCharacter?.name || '' }}</h3>
              <p class="text-caption mt-1">
                {{ activeCharacter ? getRealmName(activeCharacter.realm) : '' }}
              </p>
            </div>
          </div>
        </header>
        <v-divider class="my-3" v-if="activeCharacter"></v-divider>
        <section v-if="activeCharacter || isAuthenticated">
          <v-list nav bg-color="foreground" class="actions">
            <v-list-item
              class="new-search-button"
              v-if="activeCharacter"
              @click="$emit('new-search')"
              title="Clear character"
              :prepend-icon="Icons.REMOVE_SEARCH"
            />
            <v-list-item
              class="log-out-button"
              v-if="isAuthenticated"
              @click="$emit('log-out')"
              title="Log out"
              :prepend-icon="Icons.LOGOUT"
            />
          </v-list>
        </section>
      </v-card-text>
    </v-card>
  </v-menu>
</template>

<style scoped lang="scss">
.card-header {
  display: flex;
  flex-direction: column;

  gap: 0.8rem;

  padding-bottom: 0 !important;
}

.active-character {
  display: flex;
  align-items: center;
  gap: 1rem;

  padding: 1rem !important;
  padding-top: 0 !important;
  padding-bottom: 0 !important;

  h3 {
    color: $primary-color;
  }

  p {
    color: $text-color;
  }
}

.new-search-button,
.log-out-button {
  color: $text-color;
}

.btag-container {
  display: flex;
  align-items: center;
  gap: 1rem;
  padding: 1rem !important;
  padding-bottom: 0 !important;
}

.btag-name {
  color: $primary-color;
}
.diciminator {
  color: $text-color;
  filter: brightness(0.8);
}

.actions {
  padding-top: 0 !important;
}
</style>
