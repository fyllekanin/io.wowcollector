<script setup lang="ts">
import { computed, inject, onBeforeUnmount, onMounted, ref, watch } from 'vue';

import { useCollectableStore } from '@/stores/collectables';

import MountFilter from '@/components/Collectables/Mount/MountFilter.vue';
import MountModal from '@/components/Collectables/Mount/MountModal.vue';
import MountProgressBar from '@/components/Collectables/Mount/MountProgressBar.vue';
import MountGridCompact from '@/containers/Collectables/Mount/MountGridCompact.vue';
import Mounts from '@/containers/Collectables/Mount/Mounts.vue';
import MountService from '@/service/MountService';

import type { MountFilters, ViewStyle, Sort } from '@/types/filters';
import type { MountCategory, MountInformation } from '@/types/collections/mounts';

import { Icons, LOCAL_STORAGE, RENDER_TYPES, SORT_TYPES } from '@/constants';
import { calculateCollectedPercentage, debounce, scrollToTop } from '@/utils';

const isTablet = inject<boolean>('$isTablet');
const isSmallTablet = inject<boolean>('$isSmallTablet');

const collectablesStore = useCollectableStore();

// Rendering
const mounts = ref<MountInformation[]>([]);
const categories = ref<MountCategory[]>([]);
const activePanels = ref<number[]>([]);
const showUpButton = ref(false);
const mountModalRef = ref();
const filterRef = ref();
const collectedMounts = computed(() => mounts.value.filter((mount) => mount.isCollected));
const viewStyle = computed(() => RENDER_TYPES[renderType.value as keyof typeof RENDER_TYPES]);
const isExpanded = computed(() => activePanels.value.length === totalPanels.value);
const totalPanels = computed(() => categoriesFiltered.value.length);

// Filtering
const renderType = ref(Number(localStorage.getItem(LOCAL_STORAGE.VIEW_STYLE)) || 0);
const categoriesFiltered = ref<MountCategory[]>([]);
const searchFilter = ref('');
const sortType = ref<Sort>();

const toggleAllPanels = () => {
  activePanels.value = isExpanded.value
    ? []
    : Array.from({ length: totalPanels.value }, (_, i) => i);
};

const mapSubCategories = (subCategory: MountCategory) => {
  return {
    ...subCategory,
    mounts: subCategory.mounts.filter((mount) =>
      mount.name.toLowerCase().includes(searchFilter.value.toLowerCase()),
    ),
  };
};

const mapRootCategories = (category: MountCategory) => {
  return {
    ...category,
    mounts: category.mounts.filter((mount) =>
      mount.name.toLowerCase().includes(searchFilter.value.toLowerCase()),
    ),
    subCategories: category.subCategories
      .map(mapSubCategories)
      .filter((subCategory) => subCategory.mounts.length),
  };
};

const filter = (filters: MountFilters) => {
  let filteredCategories = categories.value;

  if (filters.search !== '') {
    searchFilter.value = filters.search;

    filteredCategories = filteredCategories
      .map(mapRootCategories)
      .filter((category) => category.mounts.length || category.subCategories.length);
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
        mounts: category.mounts.filter((mount) => {
          if (filters.miscFilters?.includes('Collected')) {
            return mount.isCollected;
          }
          if (filters.miscFilters?.includes('Not Collected')) {
            return !mount.isCollected;
          }
          return true;
        }),
        subCategories: category.subCategories.map((subCategory) => {
          return {
            ...subCategory,
            mounts: subCategory.mounts.filter((mount) => {
              if (filters.miscFilters?.includes('Collected')) {
                return mount.isCollected;
              }
              if (filters.miscFilters?.includes('Not Collected')) {
                return !mount.isCollected;
              }
              return true;
            }),
          };
        }),
      };
    });
  }

  filteredCategories = filteredCategories.filter(
    (category) => category.mounts.length || category.subCategories.some((sub) => sub.mounts.length),
  );

  filteredCategories.forEach(sortMounts);

  categoriesFiltered.value = filteredCategories;
};

const debouncedFilter = debounce(filter, 500);

const updateFilters = (filters: MountFilters) => {
  if (filters.search !== searchFilter.value) {
    debouncedFilter(filters);
    return;
  }
  filter(filters);
};

const sortMounts = (category: MountCategory) => {
  category.mounts.sort((a, b) => {
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

  category.subCategories.forEach(sortMounts);
};

const highlightFilters = () => {
  filterRef.value.highlight = true;
  setTimeout(() => {
    filterRef.value.highlight = false;
  }, 500);
};

const updateList = () => {
  const allMounts: MountInformation[] = [];
  const traverseCategory = (category: MountCategory) => {
    allMounts.push(...category.mounts);
    category.subCategories.forEach(traverseCategory);
  };
  categories.value.forEach(traverseCategory);
  mounts.value = allMounts;
};

const checkScroll = () => {
  showUpButton.value = window.scrollY >= 100;
};

const showMountDetails = async (mount: MountInformation) => {
  mountModalRef.value.open(mount, await MountService.getMountInformation(mount.id));
};

const filterDrawerAction = (value: boolean) => {
  filterRef.value.filterDrawer = value;
};

onMounted(async () => {
  window.addEventListener('scroll', checkScroll);
  categories.value = collectablesStore.mountCategories;
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
      <label for="mount-progression" class="f-size-large bold">Total Collected</label>
      <v-progress-linear
        :model-value="calculateCollectedPercentage(mounts)"
        color="primary"
        height="20"
      >
        <strong>{{ collectedMounts.length }} / {{ mounts.length }}</strong>
      </v-progress-linear>
    </div>
  </div>
  <div class="container">
    <!-- Filtering -->
    <MountFilter ref="filterRef" @filter="updateFilters" />
    <!-- Mount Summary -->
    <section class="mount-summary">
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
          <v-btn
            class="expand-button"
            variant="text"
            height="48"
            @click="toggleAllPanels"
            :density="isSmallTablet ? 'compact' : 'default'"
            :disabled="renderType === 2"
            ><v-icon size="large">{{ !isExpanded ? 'mdi-expand-all' : 'mdi-collapse-all' }}</v-icon>
            <p class="f-size-xs">{{ !isExpanded ? 'Expand All' : 'Collapse All' }}</p></v-btn
          >
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

          <!-- Render Types -->
          <v-btn-toggle v-model="renderType" variant="text" mandatory>
            <v-tooltip text="Compact Grid View" location="top" :open-delay="500">
              <template v-slot:activator="{ props }">
                <v-btn v-bind="props" variant="text" :icon="Icons.GRID_COMPACT" />
              </template>
            </v-tooltip>
            <v-tooltip text="Grid View" location="top" :open-delay="500">
              <template v-slot:activator="{ props }">
                <v-btn v-bind="props" variant="text" :icon="Icons.GRID" />
              </template>
            </v-tooltip>
            <v-tooltip text="List View" location="top" :open-delay="500">
              <template v-slot:activator="{ props }">
                <v-btn v-bind="props" variant="text" :icon="Icons.LIST" />
              </template>
            </v-tooltip>
          </v-btn-toggle>
        </div>
      </v-container>
      <v-container v-if="categoriesFiltered.length === 0">
        <v-row justify="center">
          <v-col cols="12" class="d-flex justify-center">
            <v-label class="f-size-large bold">No mounts found</v-label>
          </v-col>
          <v-col cols="12" class="d-flex justify-center">
            <v-label class="f-size-large bold">Try changing your filters</v-label>
          </v-col>
        </v-row>
      </v-container>
      <v-container v-else-if="viewStyle === 'grid-compact'">
        <MountGridCompact
          v-for="(category, index) in categoriesFiltered"
          :key="index"
          :category="category"
          @show-mount-details="(mount: MountInformation) => $emit('show-mount-details', mount)"
        />
      </v-container>
      <v-expansion-panels v-else multiple variant="accordion" v-model="activePanels">
        <v-expansion-panel
          v-for="(category, index) in categoriesFiltered"
          :key="index"
          bg-color="foreground"
        >
          <v-expansion-panel-title>
            <v-container class="expansion-panel-title">
              <v-row>
                <v-col class="d-flex justify-start">
                  <v-label class="f-size-small bold">{{ category.name }}</v-label>
                </v-col>
                <v-col cols="6" v-if="!isSmallTablet">
                  <MountProgressBar :category="category" />
                </v-col>
                <v-spacer></v-spacer>
              </v-row>
            </v-container>
          </v-expansion-panel-title>
          <v-expansion-panel-text>
            <MountProgressBar v-if="isSmallTablet" :category="category" />
            <Mounts
              :category="category"
              :view-style="viewStyle as ViewStyle"
              @show-mount-details="showMountDetails"
            />
          </v-expansion-panel-text>
        </v-expansion-panel>
      </v-expansion-panels>
      <v-scroll-x-transition>
        <v-btn
          v-if="(showUpButton && activePanels.length) || (showUpButton && renderType === 2)"
          class="up-button"
          icon="mdi-arrow-up"
          color="primary"
          @click="scrollToTop"
        />
      </v-scroll-x-transition>
    </section>
  </div>
  <MountModal ref="mountModalRef" />
</template>

<style scoped lang="scss" src="./MountLayout.scss"></style>
