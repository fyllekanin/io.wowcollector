<script setup lang="ts">
import { ref, inject, watch } from 'vue';

const isTablet = inject<boolean>('$isTablet');

const filterDrawer = ref(false);
const highlight = ref(false);

defineExpose({
  filterDrawer,
  highlight,
});

const emit = defineEmits(['drawer']);

watch(filterDrawer, (value) => {
  emit('drawer', value);
});
</script>

<template>
  <v-navigation-drawer
    v-if="isTablet"
    class="filters"
    v-model="filterDrawer"
    temporary
    color="foreground"
  >
    <v-card-title>
      <slot name="title" />
    </v-card-title>
    <v-spacer></v-spacer>
    <v-card-text>
      <slot name="content" />
    </v-card-text>
  </v-navigation-drawer>
  <aside v-else class="filters">
    <v-card :class="`filters-card ${highlight ? 'highlight' : ''}`" color="foreground">
      <v-card-title>
        <slot name="title" />
      </v-card-title>
      <v-spacer></v-spacer>
      <v-card-text>
        <slot name="content" />
      </v-card-text>
    </v-card>
  </aside>
</template>

<style scoped lang="scss">
.filters {
  min-width: 300px !important;
  max-width: 300px !important;
}

.filters-card {
  @media (min-width: 990px) {
    transition: filter 0.2s ease-in-out;
    position: sticky !important;
    top: calc(1rem + 64px) !important;
  }
}

.filters-card.highlight {
  transition: filter 0.15s ease-in-out;
  filter: brightness(1.8);
}
</style>
