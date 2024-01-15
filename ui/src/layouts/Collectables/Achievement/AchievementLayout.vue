<script setup lang="ts">
import { computed, inject, onBeforeUnmount, onMounted, ref, watch } from 'vue';

import { useCollectableStore } from '@/stores/collectables';

import AchievementFilter from '@/components/Collectables/Achievement/AchievementFilter.vue';
import AchievementGridCompact from '@/containers/Collectables/Achievement/AchievementGridCompact.vue';

import { SORT_TYPES } from '@/constants';
import { calculateCollectedPercentage, debounce, scrollToTop } from '@/utils';

import type { Filters, Sort } from '@/types/filters';
import type { AchievementCategory, AchievementInformation } from '@/types/collections/achievements';

const isTablet = inject<boolean>('$isTablet');
const isSmallTablet = inject<boolean>('$isSmallTablet');

// Rendering
const achievements = ref<AchievementInformation[]>([]);
const categories = ref<AchievementCategory[]>([]);
const collectablesStore = useCollectableStore();
const collectedAchievements = computed(() =>
  achievements.value.filter((achievement) => achievement.isCollected),
);
const showUpButton = ref(false);
const filterRef = ref();

// Filtering
const searchFilter = ref('');
const categoriesFiltered = ref<AchievementCategory[]>([]);
const sortType = ref<Sort>();

const mapSubCategories = (subCategory: AchievementCategory) => {
  return {
    ...subCategory,
    achievements: subCategory.achievements.filter((achievement) =>
      achievement.name.toLowerCase().includes(searchFilter.value.toLowerCase()),
    ),
  };
};

const mapRootCategories = (category: AchievementCategory) => {
  return {
    ...category,
    achievements: category.achievements.filter((achievement) =>
      achievement.name.toLowerCase().includes(searchFilter.value.toLowerCase()),
    ),
    subCategories: category.subCategories
      .map(mapSubCategories)
      .filter((subCategory) => subCategory.achievements.length),
  };
};

const filter = (filters: Filters) => {
  let filteredCategories = categories.value;

  if (filters.search !== '') {
    searchFilter.value = filters.search;

    filteredCategories = filteredCategories
      .map(mapRootCategories)
      .filter((category) => category.achievements.length || category.subCategories.length);
  }

  if (filters.rootCategories.length) {
    filteredCategories = filteredCategories.filter(
      (category) => filters.rootCategories?.includes(category.name),
    );
  }

  if (filters.subCategories.length) {
    filteredCategories = filteredCategories.map((category) => {
      return {
        ...category,
        subCategories: category.subCategories.filter(
          (subCategory) => filters.subCategories?.includes(subCategory.name),
        ),
      };
    });
  }

  if (filters.miscFilters.length) {
    filteredCategories = filteredCategories.map((category) => {
      return {
        ...category,
        achievements: category.achievements.filter((achievement) => {
          if (filters.miscFilters?.includes('Collected')) {
            return achievement.isCollected;
          }
          if (filters.miscFilters?.includes('Not Collected')) {
            return !achievement.isCollected;
          }
          return true;
        }),
        subCategories: category.subCategories.map((subCategory) => {
          return {
            ...subCategory,
            achievements: subCategory.achievements.filter((achievement) => {
              if (filters.miscFilters?.includes('Collected')) {
                return achievement.isCollected;
              }
              if (filters.miscFilters?.includes('Not Collected')) {
                return !achievement.isCollected;
              }
              return true;
            }),
          };
        }),
      };
    });
  }

  filteredCategories = filteredCategories.filter(
    (category) =>
      category.achievements.length || category.subCategories.some((sub) => sub.achievements.length),
  );

  const sortAchievements = (category: AchievementCategory) => {
    category.achievements.sort((a, b) => {
      switch (sortType.value) {
        case 'Collected':
          if (a.isCollected && !b.isCollected) return -1;
          if (!a.isCollected && b.isCollected) return 1;
          return 0;
        case 'Not Collected':
          if (a.isCollected && !b.isCollected) return 1;
          if (!a.isCollected && b.isCollected) return -1;
          return 0;
        case 'Name Ascending':
          return a.name.localeCompare(b.name);
        case 'Name Descending':
          return b.name.localeCompare(a.name);
        default:
          return 0;
      }
    });

    category.subCategories.forEach(sortAchievements);
  };

  filteredCategories.forEach(sortAchievements);

  categoriesFiltered.value = filteredCategories;
};

const debouncedFilter = debounce(filter, 500);

const updateFilters = (filters: Filters) => {
  if (filters.search !== searchFilter.value) {
    debouncedFilter(filters);
    return;
  }
  filter(filters);
};

const highlightFilters = () => {
  filterRef.value.highlight = true;
  setTimeout(() => {
    filterRef.value.highlight = false;
  }, 500);
};

const updateList = () => {
  const allAchievements: AchievementInformation[] = [];
  const traverseCategory = (category: AchievementCategory) => {
    allAchievements.push(...category.achievements);
    category.subCategories.forEach(traverseCategory);
  };
  categories.value.forEach(traverseCategory);
  achievements.value = allAchievements;
};

const checkScroll = () => {
  showUpButton.value = window.scrollY >= 100;
};

const filterDrawerAction = (value: boolean) => {
  filterRef.value.filterDrawer = value;
};

onMounted(async () => {
  window.addEventListener('scroll', checkScroll);
  categories.value = collectablesStore.achievementCategories;
  categoriesFiltered.value = categories.value;
  updateList();
});

onBeforeUnmount(() => {
  window.removeEventListener('scroll', checkScroll);
});

watch([sortType], ([value]) => {
  filterRef.value.setSortType(value);
});
</script>

<template>
  <div class="progress">
    <v-divider color="text" class="divider"></v-divider>
    <div class="total-collected">
      <label for="achievement-progression" class="f-size-large bold">Total Collected</label>
      <v-progress-linear
        :model-value="calculateCollectedPercentage(achievements)"
        color="primary"
        height="20"
      >
        <strong>{{ collectedAchievements.length }} / {{ achievements.length }}</strong>
      </v-progress-linear>
    </div>
  </div>
  <div class="container">
    <!-- Filtering -->
    <AchievementFilter ref="filterRef" @filter="updateFilters" />
    <!-- Mount Summary -->
    <section class="achievement-summary">
      <v-container class="sorting-and-filtering">
        <div class="filtering">
          <v-btn
            class="filter-button"
            size="x-large"
            color="white"
            variant="text"
            :density="isSmallTablet ? 'compact' : 'default'"
            @click="() => (isTablet ? filterDrawerAction(true) : highlightFilters())"
          >
            <v-icon>mdi-filter</v-icon>
            <p class="f-size-xs">Filter</p>
          </v-btn>
        </div>
        <div class="sorting">
          <v-select
            v-model="sortType"
            :items="SORT_TYPES"
            variant="outlined"
            label="Sort By"
            hide-details
            :density="isSmallTablet ? 'compact' : 'default'"
          >
          </v-select>
        </div>
      </v-container>
      <v-container v-if="categoriesFiltered.length === 0">
        <v-row justify="center">
          <v-col cols="12" class="d-flex justify-center">
            <v-label class="f-size-large bold">No achievements found</v-label>
          </v-col>
          <v-col cols="12" class="d-flex justify-center">
            <v-label class="f-size-large bold">Try changing your filters</v-label>
          </v-col>
        </v-row>
      </v-container>
      <v-container>
        <AchievementGridCompact
          v-for="(category, index) in categoriesFiltered"
          :key="index"
          :category="category"
        />
      </v-container>
      <v-scroll-x-transition>
        <v-btn
          v-if="showUpButton"
          class="up-button"
          icon="mdi-arrow-up"
          color="primary"
          @click="scrollToTop"
        />
      </v-scroll-x-transition>
    </section>
  </div>
</template>

<style scoped lang="scss" src="./AchievementLayout.scss"></style>
