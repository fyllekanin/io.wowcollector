<script setup lang="ts">
import type { MountCategory } from '@/types/collections/mounts';
import type { PropType } from 'vue';

import { computed } from 'vue';
import { calculateCollectedPercentage } from '@/utils/calculators';

const props = defineProps({
  category: {
    type: Object as PropType<MountCategory>,
    required: true,
  },
});

const getCollectedAndAllMounts = (category: MountCategory) => {
  const allMounts = [...category.mounts, ...category.subCategories.flatMap((sub) => sub.mounts)];
  const collected = allMounts.filter((mount) => mount.isCollected);
  return {
    collected,
    allMounts,
  };
};
const calculateMountDataForCategory = (category: MountCategory) => {
  const { collected, allMounts } = getCollectedAndAllMounts(category);
  return {
    collected: collected.length,
    total: allMounts.length,
  };
};

const category = computed(() => props.category);
const percentage = computed(() =>
  calculateCollectedPercentage([
    ...category.value.mounts,
    ...category.value.subCategories.flatMap((sub) => sub.mounts),
  ]),
);
const collected = computed(() => calculateMountDataForCategory(category.value).collected);
const total = computed(() => calculateMountDataForCategory(category.value).total);
</script>

<template>
  <v-progress-linear
    :model-value="percentage"
    :color="collected === total && total > 0 ? 'primary' : 'text'"
    height="15"
  >
    <strong>
      {{ collected }}
      /
      {{ total }}
    </strong>
  </v-progress-linear>
</template>
