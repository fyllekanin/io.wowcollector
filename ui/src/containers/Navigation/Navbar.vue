<script setup lang="ts">
import { inject, ref } from 'vue';
import { storeToRefs } from 'pinia';
import router from '@/router';

import ConfirmationModal from '@/components/ConfirmationModal.vue';
import CharacterMenu from '@/components/Navigation/CharacterMenu.vue';
import Sidebar from './Sidebar.vue';

import { useAuthStore, useCharacterStore, useTabStore } from '@/stores';
import { newSearch, goTo, login, logout, upperCaseFirst, isAlreadyOnPath } from '@/utils';
import { Icons, getTabs } from '@/constants';

const isSmallTablet = inject('$isSmallTablet');

const tabStore = useTabStore();
const characterStore = useCharacterStore();
const authStore = useAuthStore();

const { activeCharacter } = storeToRefs(characterStore);
const { _tab } = storeToRefs(tabStore);
const { isAuthenticated } = storeToRefs(authStore);

const clearCharacterModalRef = ref();
const logoutModalRef = ref();
const sidebarRef = ref();

const clearCharacterModal = () => {
  clearCharacterModalRef.value?.open();
};
const logoutModal = () => {
  logoutModalRef.value?.open();
};
const showDrawer = () => {
  sidebarRef.value?.showDrawer();
};
</script>

<template>
  <v-app-bar color="foreground" scroll-behavior="elevate">
    <div class="appbar-title-wrapper">
      <v-app-bar-title>
        <div class="logo d-flex align-center" @click="goTo('/')">
          <v-icon icon="custom:logo" size="48"></v-icon>
          <h4>WoW Collector</h4>
          <p>Beta</p>
        </div>
      </v-app-bar-title>
    </div>
    <v-spacer></v-spacer>
    <v-tabs v-if="!isSmallTablet" v-model="_tab" show-arrows>
      <template v-for="(tab, index) in getTabs()">
        <v-tab
          v-if="!tab.isMenu"
          class="pl-2 pr-2"
          color="primary"
          :key="index"
          :value="tab.route"
          @click="router.push({ path: tab.route }).catch(() => {})"
        >
          <p>{{ tab.alias }}</p>
        </v-tab>
        <v-tab
          v-else
          class="menu-tab pa-0"
          color="primary"
          :ripple="false"
          :key="tab.alias"
          :value="tab.route"
          @click="router.push({ path: tab.route }).catch(() => {})"
        >
          <v-menu>
            <template v-slot:activator="{ props, isActive }">
              <v-btn
                class="pl-2 pr-2"
                :color="_tab === tab.route ? 'primary' : 'white'"
                variant="text"
                rounded="0"
                height="100%"
                :ripple="false"
                v-bind="props"
              >
                {{ tab.alias }}
                <v-icon end color="white">{{
                  isActive ? Icons.CHEVRON_UP : Icons.CHEVRON_DOWN
                }}</v-icon>
              </v-btn>
            </template>

            <v-list bg-color="foreground">
              <v-list-item
                v-for="childTab in tab.children"
                :key="childTab.alias"
                :prepend-icon="childTab.icon"
                @click="!isAlreadyOnPath(childTab.route) && goTo(childTab.route)"
                link
              >
                {{ upperCaseFirst(childTab.alias) }}
              </v-list-item>
            </v-list>
          </v-menu>
        </v-tab>
      </template>
    </v-tabs>
    <v-tab
      v-if="!isSmallTablet && !isAuthenticated"
      class="login-tab"
      variant="text"
      height="64"
      :prepend-icon="Icons.BATTLENET"
      @click="login"
    >
      Connect</v-tab
    >
    <CharacterMenu
      v-if="(activeCharacter && !isSmallTablet) || (isAuthenticated && !isSmallTablet)"
      @new-search="clearCharacterModal"
      @log-out="logoutModal"
    />
    <v-app-bar-nav-icon v-else-if="isSmallTablet" @click="showDrawer">
      <v-icon icon="mdi-menu" size="24" />
    </v-app-bar-nav-icon>
  </v-app-bar>
  <Sidebar ref="sidebarRef" v-if="isSmallTablet" />

  <ConfirmationModal
    ref="clearCharacterModalRef"
    title="Clear character"
    text="Are you sure you want to switch character?"
    confirm-text="Clear"
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

<style lang="scss">
.logo {
  cursor: pointer;

  svg {
    margin-bottom: 5px;
  }

  h4 {
    text-transform: uppercase;
    font-size: $font-small;
  }

  p {
    text-transform: uppercase;
    font-size: $font-xx-small;
    margin-left: 5px;
    margin-bottom: 12px;
    font-weight: 600;
  }
}

.login-tab {
  gap: 0.5rem !important;
}

.appbar-title-wrapper {
  display: flex;
  align-items: center;
  justify-content: flex-start;
  flex-shrink: 0;
}

.menu-tab {
  .v-btn__content {
    height: 100% !important;
    width: 100% !important;
  }
}
</style>
