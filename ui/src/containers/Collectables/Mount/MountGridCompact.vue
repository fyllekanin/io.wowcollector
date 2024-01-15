<script lang="ts" setup>
import { computed } from 'vue';

import type { PropType } from 'vue';
import type { MountCategory } from '@/types/collections/mounts';

const props = defineProps({
  category: {
    type: Object as PropType<MountCategory>,
    required: true,
  },
});
defineEmits(['show-mount-details']);

const sortedCategory = computed(() => {
  const sortedSubCategories = [...props.category.subCategories].sort((a, b) => {
    if (a.mounts?.length > b.mounts?.length) return -1;
    if (a.mounts?.length < b.mounts?.length) return 1;
    return 0;
  });

  return {
    ...props.category,
    subCategories: sortedSubCategories,
  };
});
</script>

<template>
  <v-container v-if="sortedCategory.subCategories.length" class="known-mounts-content">
    <v-label class="f-size-large bold">{{ sortedCategory.name }}</v-label>
    <v-sheet class="sheet-container grid-container" color="background">
      <div
        class="sub-cat-grid-item"
        v-for="(subCategory, index) in sortedCategory.subCategories"
        :key="index"
      >
        <v-label class="f-size-small bold">{{ subCategory.name }}</v-label>
        <div class="mount-row">
          <div
            v-for="(mount, index) in subCategory.mounts"
            :key="index"
            class="d-flex justify-start flex-grow-0 pa-1"
          >
            <v-lazy :options="{ threshold: 0.5 }">
              <a
                :href="`https://www.wowhead.com/mount/${mount.id}`"
                target="_blank"
                :data-wowhead="`mount=${mount.id}`"
              >
                <v-img
                  :src="mount.icon"
                  :lazy-src="mount.icon"
                  :class="[!mount.isCollected ? 'not-collected' : '', 'mount-icon']"
                  cover
                  aspect-ratio="1/1"
                  width="32"
                  :on-error="(e: any) => (e.target.src = mount.creatureDisplay)"
                  @error="
                    mount.icon =
                      'https://wow.zamimg.com/images/wow/icons/large/inv_misc_questionmark.jpg'
                  "
                />
              </a>
            </v-lazy>
          </div>
        </div>
      </div>
    </v-sheet>
  </v-container>

  <v-container v-if="category.mounts.length" class="unknown-mounts-content">
    <v-label class="f-size-large bold">{{ category.name }}</v-label>
    <v-sheet class="sheet-container grid-container" color="background">
      <div class="mount-row">
        <div
          v-for="(mount, index) in category.mounts"
          :key="index"
          class="d-flex justify-start flex-grow-0 pa-1"
        >
          <v-lazy :options="{ threshold: 0.5 }">
            <a
              :href="`https://www.wowhead.com/mount/${mount.id}`"
              target="_blank"
              :data-wowhead="`mount=${mount.id}`"
            >
              <v-img
                :src="mount.icon"
                :lazy-src="mount.icon"
                :class="[!mount.isCollected ? 'not-collected' : '', 'mount-icon']"
                cover
                aspect-ratio="1/1"
                width="32"
                :on-error="(e: any) => (e.target.src = mount.creatureDisplay)"
                @error="
                  mount.icon =
                    'https://wow.zamimg.com/images/wow/icons/large/inv_misc_questionmark.jpg'
                "
              />
            </a>
          </v-lazy>
        </div>
      </div>
    </v-sheet>
  </v-container>

  <v-divider class="divider"></v-divider>
</template>

<style lang="scss" scoped>
.known-mounts-content,
.unknown-mounts-content {
  padding: 0;
  display: grid;
  grid-gap: 1.5rem;
}

.grid-container {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
  grid-gap: 1rem;
}

.sub-cat-grid-item {
  display: flex;
  flex-direction: column;
}

.mount-row {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(32px, 1fr));
  grid-gap: 0.5rem;
}

.divider {
  margin-top: 1rem;
  margin-bottom: 1rem;
}

.mount-icon {
  cursor: pointer;
}

.not-collected {
  filter: contrast(0) brightness(20%);
  transition: filter 0.5s ease;
  &:hover {
    transition: filter 0.5s ease;
    filter: contrast(1) brightness(100%);
  }
}
</style>
