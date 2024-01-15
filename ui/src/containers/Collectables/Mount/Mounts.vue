<script lang="ts" setup>
import type { PropType } from 'vue';
import type { MountCategory, MountInformation } from '@/types/collections/mounts';
import type { ViewStyle } from '@/types/filters';

import MountList from './MountList.vue';
import MountGrid from './MountGrid.vue';
import { LOCAL_STORAGE } from '@/constants';

defineProps({
  category: {
    type: Object as PropType<MountCategory>,
    required: true,
  },
  viewStyle: {
    type: String as PropType<ViewStyle>,
    default: (localStorage.getItem(LOCAL_STORAGE.VIEW_STYLE) || 'grid-compact') as ViewStyle,
  },
});
defineEmits(['show-mount-details']);
</script>

<template>
  <MountGrid
    v-if="viewStyle === 'grid' && (category.subCategories.length || category.mounts.length)"
    :category="category"
    @show-mount-details="(mount: MountInformation) => $emit('show-mount-details', mount)"
  />
  <MountList
    v-else-if="viewStyle === 'list' && (category.subCategories.length || category.mounts.length)"
    :category="category"
    @show-mount-details="(mount: MountInformation) => $emit('show-mount-details', mount)"
  />
</template>

<style lang="scss" scoped>
.mount-card {
  cursor: pointer;
}

.not-collected {
  filter: grayscale(100%) blur(6px);
  transition: filter 0.5s ease;
  &:hover {
    transition: filter 0.5s ease;
    filter: grayscale(100%) blur(0px);
  }
}
</style>
