<script setup lang="ts">
import { RouterView } from 'vue-router';
import { storeToRefs } from 'pinia';

import Navbar from '@/containers/Navigation/Navbar.vue';

import { useLoadingStore } from '@/stores/loading';

const loadingStore = useLoadingStore();

const { loading } = storeToRefs(loadingStore);
</script>

<template>
  <v-app class="v-app">
    <Navbar />
    <div class="container">
      <v-overlay
        v-if="loading"
        :model-value="loading"
        class="align-center justify-center"
        persistent
      >
        <v-progress-circular color="primary" indeterminate size="64"></v-progress-circular>
      </v-overlay>
      <RouterView v-else />
    </div>
  </v-app>
</template>

<style scoped lang="scss">
.container {
  max-width: 1200px;
  margin: 0 auto;
  width: 100%;

  @media (min-width: 600px) {
    margin-top: 64px;
  }

  @media (max-width: 480px) {
    margin-bottom: 10rem;
  }
}

.v-app {
  background-color: $background-color;
}
</style>
