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
        <v-list lines="three" bg-color="foreground">
          <v-list-item
            v-for="(mount, index) in subCategory.mounts"
            :key="index"
            class="mount-list-item d-flex justify-start flex-grow-0 pa-0 mt-5"
          >
            <template v-slot:prepend>
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
              </v-img>
            </template>
            <v-list-item-subtitle class="mount-list-item-content">
              <v-list-item-title>{{ mount.name }}</v-list-item-title>
              <v-list-item-subtitle>{{ mount.description }}</v-list-item-subtitle>
              <v-list-item-subtitle
                >Collected:
                <v-icon
                  :icon="mount.isCollected ? 'mdi-check' : 'mdi-close'"
                  :color="mount.isCollected ? 'green' : 'red'"
              /></v-list-item-subtitle>
            </v-list-item-subtitle>
          </v-list-item>
        </v-list>
      </v-container>
    </v-col>
  </v-container>
  <v-container v-if="category.mounts.length">
    <v-list lines="three" bg-color="foreground">
      <v-list-item
        v-for="(mount, index) in category.mounts"
        :key="index"
        class="mount-list-item d-flex justify-start flex-grow-0 pa-0"
      >
        <template v-slot:prepend>
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
          </v-img>
        </template>
        <v-list-item-subtitle class="mount-list-item-content">
          <v-list-item-title>{{ mount.name }}</v-list-item-title>
          <v-list-item-subtitle>{{ mount.description }}</v-list-item-subtitle>
          <v-list-item-subtitle
            >Collected:
            <v-icon
              :icon="mount.isCollected ? 'mdi-check' : 'mdi-close'"
              :color="mount.isCollected ? 'green' : 'red'"
          /></v-list-item-subtitle>
        </v-list-item-subtitle>
      </v-list-item>
    </v-list>
  </v-container>
</template>

<style lang="scss" scoped>
.mount-card {
  cursor: pointer;
}

.mount-list-item {
  gap: 2rem !important;

  .mount-list-item-content {
    display: flex;
    flex-direction: column;
    gap: 0.5rem !important;

    .v-list-item-title {
      white-space: pre-wrap;
      word-break: keep-all;
    }
  }
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
