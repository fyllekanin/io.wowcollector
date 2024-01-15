<script lang="ts" setup>
import { ref, inject } from 'vue';

import { Icons, LOCAL_STORAGE } from '@/constants';

const isMobile = inject<boolean>('$isMobile');

const viewStyle = ref<number>(Number(localStorage.getItem(LOCAL_STORAGE.VIEW_STYLE)) || 0);
const tooltipStyle = ref<number>(Number(localStorage.getItem(LOCAL_STORAGE.TOOLTIP_STYLE)) || 0);
</script>

<template>
  <header>
    <h2 class="d-flex justify-self-left">Preferences</h2>
  </header>
  <v-container class="preferences">
    <!-- View Style Preference -->
    <div class="view-style-preference">
      <v-label>View style</v-label>
      <v-btn-toggle v-model="viewStyle" variant="text" mandatory>
        <v-btn variant="text" :icon="Icons.GRID" />
        <v-btn variant="text" :icon="Icons.LIST" />
        <v-btn variant="text" :icon="Icons.GRID_COMPACT" />
      </v-btn-toggle>
    </div>
    <!-- Tooltip Style Preference -->
    <div class="tooltip-style-preference">
      <v-label>Tooltip style</v-label>
      <v-btn-toggle v-model="tooltipStyle" variant="text" mandatory>
        <v-tooltip location="top" text="Simple">
          <template v-slot:activator="{ props }">
            <v-btn variant="text" :icon="Icons.TOOLTIP_TEXT" v-bind="props" />
          </template>
        </v-tooltip>
        <v-tooltip :location="isMobile ? 'top' : 'left'" text="WoW Styled">
          <template v-slot:activator="{ props }">
            <a role="button" tabindex="0" href="#" data-wowhead="mount=100">
              <v-btn
                style="height: 100%"
                variant="text"
                :icon="Icons.TOOLTIP_IMAGE"
                v-bind="props"
              />
            </a>
          </template>
        </v-tooltip>
      </v-btn-toggle>
    </div>
  </v-container>
</template>

<style lang="scss" scoped src="./Preferences.scss"></style>
