<script lang="ts" setup>
import { ref, computed } from 'vue';
import { storeToRefs } from 'pinia';

import ConfirmationModal from '@/components/ConfirmationModal.vue';

import { Icons, getTabs } from '@/constants';
import { goTo, login, logout, newSearch, upperCaseFirst, isAlreadyOnPath } from '@/utils';

import { useAuthStore, useRealmStore, useCharacterStore } from '@/stores';

defineExpose({
  showDrawer() {
    showDrawer.value = true;
  },
});

const characterStore = useCharacterStore();
const realmStore = useRealmStore();
const authStore = useAuthStore();

const { activeCharacter } = storeToRefs(characterStore);
const { isAuthenticated, user } = storeToRefs(authStore);

const confirmationModalRef = ref();
const logoutModalRef = ref();
const showDrawer = ref(false);

const realmName = computed(
  () => realmStore.realms.find((realm) => realm.slug === activeCharacter.value?.realm)?.name,
);
const avatar = computed(() => activeCharacter.value?.media?.avatar ?? '');

const logoutModal = () => {
  logoutModalRef.value?.open();
};
</script>

<template>
  <v-navigation-drawer v-model="showDrawer" temporary color="foreground">
    <v-list-item
      v-if="isAuthenticated"
      :prepend-icon="Icons.BATTLENET"
      :title="user?.battleTag"
    ></v-list-item>
    <v-divider></v-divider>
    <v-list-item
      v-if="activeCharacter"
      :prepend-avatar="activeCharacter?.media?.avatar ? avatar : ''"
      :prepend-icon="!activeCharacter?.media?.avatar ? Icons.ACCOUNT_CIRCLE : ''"
      :title="activeCharacter?.name"
      :subtitle="realmName"
    ></v-list-item>

    <v-divider></v-divider>

    <v-list nav bg-color="foreground">
      <template v-for="tab in getTabs()" :key="tab.route">
        <v-list-item
          v-if="!tab.isMenu"
          :prepend-icon="tab.icon"
          :title="upperCaseFirst(tab.alias)"
          :value="tab.alias"
          @click="goTo(tab.route)"
        ></v-list-item>
        <v-list-group v-else>
          <template v-slot:activator="{ props }">
            <v-list-item
              v-bind="props"
              :prepend-icon="tab.icon"
              :title="upperCaseFirst(tab.alias)"
            />
          </template>
          <v-list-item
            v-for="childTab in tab.children"
            :key="childTab.alias"
            :prepend-icon="childTab.icon"
            link
            @click="!isAlreadyOnPath(childTab.route) && goTo(childTab.route)"
            :title="upperCaseFirst(childTab.alias)"
          />
        </v-list-group>
      </template>
      <v-list-item
        v-if="!isAuthenticated"
        :prepend-icon="Icons.BATTLENET"
        title="Connect"
        @click="login"
      ></v-list-item>
      <v-list-item
        v-if="isAuthenticated"
        :prepend-icon="Icons.LOGOUT"
        title="Log out"
        @click="logoutModal"
      ></v-list-item>
    </v-list>
  </v-navigation-drawer>
  <ConfirmationModal
    ref="confirmationModalRef"
    title="Switch character"
    text="Are you sure you want to switch character?"
    confirm-text="Switch"
    cancel-text="Cancel"
    @confirm="newSearch"
  />
  <ConfirmationModal
    ref="logoutModalRef"
    title="Log out"
    text="Are you sure you want to log out?"
    confirm-text="Log out"
    cancel-text="Cancel"
    @confirm="logout"
  />
</template>
