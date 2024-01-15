<script lang="ts" setup>
import type { PropType } from 'vue';
import type { MountCategory } from '@/types/collections/mounts';

defineProps({
  category: {
    type: Object as PropType<MountCategory>,
    required: true,
  },
});
defineEmits(['show-mount-details']);
</script>

<template>
  <v-container v-if="category.subCategories.length" class="expansion-panel-content pa-0">
    <v-col v-for="(subCategory, index) in category.subCategories" :key="index">
      <v-container class="sub-category pa-0">
        <v-label class="f-size-small bold">{{ subCategory.name }}</v-label>
        <v-row class="mount-section">
          <v-col
            v-for="(mount, index) in subCategory.mounts"
            :key="index"
            class="d-flex justify-start flex-grow-0"
          >
            <v-sheet width="100" height="100">
              <v-lazy :options="{ threshold: 0.5 }">
                <v-img
                  :src="mount.creatureDisplay"
                  :lazy-src="mount.creatureDisplay"
                  :class="`${!mount.isCollected ? 'not-collected' : ''} mount-card`"
                  :data-wowhead="`mount=${mount.id}`"
                  width="100"
                  cover
                  aspect-ratio="1/1"
                  @click="() => $emit('show-mount-details', mount)"
                  @error="
                    () =>
                      (mount.creatureDisplay =
                        'https://wow.zamimg.com/images/wow/icons/large/inv_misc_questionmark.jpg')
                  "
                >
                  <v-tooltip activator="parent" location="top">{{ mount.name }}</v-tooltip>
                </v-img>
              </v-lazy>
            </v-sheet>
          </v-col>
        </v-row>
      </v-container>
    </v-col>
  </v-container>
  <v-container v-if="category.mounts.length" class="expansion-panel-content">
    <v-row>
      <v-col
        v-for="(mount, index) in category.mounts"
        :key="index"
        class="d-flex justify-center flex-grow-0"
      >
        <v-sheet width="100" height="100">
          <v-lazy :options="{ threshold: 0.5 }">
            <v-img
              :src="mount.creatureDisplay"
              :class="`${!mount.isCollected ? 'not-collected' : ''} mount-card`"
              :alt="mount.name"
              :data-wowhead="`mount=${mount.id}`"
              width="100"
              cover
              aspect-ratio="1/1"
              transition="slide-x-transition"
              :data-mount-id="mount.id"
              @click="() => $emit('show-mount-details', mount)"
              @error="
                () =>
                  (mount.creatureDisplay =
                    'https://wow.zamimg.com/images/wow/icons/large/inv_misc_questionmark.jpg')
              "
            >
              <v-tooltip activator="parent" location="top">{{ mount.name }}</v-tooltip>
            </v-img>
          </v-lazy>
        </v-sheet>
      </v-col>
    </v-row>
  </v-container>
</template>

<style lang="scss" scoped>
.mount-card {
  cursor: pointer;
}

.mount-section {
  padding-top: 0.75rem;
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
