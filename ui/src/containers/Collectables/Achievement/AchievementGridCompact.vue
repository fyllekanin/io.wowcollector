<script lang="ts" setup>
import { computed } from 'vue';

import type { PropType } from 'vue';
import type { AchievementCategory } from '@/types/collections/achievements';

const props = defineProps({
  category: {
    type: Object as PropType<AchievementCategory>,
    required: true,
  },
});

const sortedCategory = computed(() => {
  const sortedSubCategories = [...props.category.subCategories].sort((a, b) => {
    if (a.achievements?.length > b.achievements?.length) return -1;
    if (a.achievements?.length < b.achievements?.length) return 1;
    return 0;
  });

  return {
    ...props.category,
    subCategories: sortedSubCategories,
  };
});
</script>

<template>
  <v-container v-if="sortedCategory.subCategories.length" class="known-achievements-content">
    <v-label class="f-size-large bold">{{ sortedCategory.name }}</v-label>
    <v-sheet class="sheet-container grid-container" color="background">
      <div
        class="sub-cat-grid-item"
        v-for="(subCategory, index) in sortedCategory.subCategories"
        :key="index"
      >
        <v-label class="f-size-small bold">{{ subCategory.name }}</v-label>
        <div class="achievement-row">
          <div
            v-for="(achievement, index) in subCategory.achievements"
            :key="index"
            class="d-flex justify-start flex-grow-0 pa-1 achievement-icon"
          >
            <v-lazy :options="{ threshold: 0.5 }">
              <a
                :href="`https://www.wowhead.com/achievement=${achievement.id}`"
                target="_blank"
                :data-wowhead="`achievement=${achievement.id}`"
              >
                <v-img
                  :src="achievement.icon"
                  :lazy-src="achievement.icon"
                  :class="[!achievement.isCollected ? 'not-collected' : '', 'achievement-icon']"
                  cover
                  aspect-ratio="1/1"
                  width="32"
                  :on-error="(e: any) => (e.target.src = achievement.icon)"
                  @error="
                    achievement.icon =
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

  <v-container v-if="category.achievements.length" class="unknown-achievements-content">
    <v-label class="f-size-large bold">{{ category.name }}</v-label>
    <v-sheet class="sheet-container grid-container" color="background">
      <div class="achievement-row">
        <div v-for="(achievement, index) in category.achievements" :key="index">
          <v-lazy :options="{ threshold: 0.5 }">
            <a
              :href="`https://www.wowhead.com/achievement=${achievement.id}`"
              target="_blank"
              :data-wowhead="`achievement=${achievement.id}`"
            >
              <v-img
                :src="achievement.icon"
                :lazy-src="achievement.icon"
                :class="[!achievement.isCollected ? 'not-collected' : '', 'achievement-icon']"
                cover
                aspect-ratio="1/1"
                width="32"
                :on-error="(e: any) => (e.target.src = achievement.icon)"
                @error="
                  achievement.icon =
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

<style lang="scss" scoped src="./AchievementGridCompact.scss"></style>
