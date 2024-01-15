<script setup lang="ts">
import { ref, watch, onMounted, computed } from 'vue';

import Filter from '@/components/Filter/Filter.vue';
import ClearFilterButton from '@/components/Filter/ClearFilterButton.vue';

import type { MountCategory } from '@/types/collections/mounts';
import { useCollectableStore } from '@/stores/collectables';

const collectablesStore = useCollectableStore();

// Rendering
const filterRef = ref();
const highlight = ref(false);
const filterDrawer = ref(false);
// Data
const categories = ref<MountCategory[]>([]);
const rootCategories = ref<string[]>([]);
const subCategories = ref<string[]>([]);
const miscFilters = ref<string[]>(['Not Collected', 'Collected']);
// Filters
const searchFilter = ref('');
const selectedRootCategories = ref<string[]>([]);
const selectedSubCategories = ref<string[]>([]);
const selectedMiscFilters = ref<string[]>([]);
const sortType = ref<string>('');

const filterNotEmpty = computed(() => {
  return (
    searchFilter.value ||
    selectedRootCategories.value.length > 0 ||
    selectedSubCategories.value.length > 0 ||
    selectedMiscFilters.value.length > 0
  );
});

defineExpose({
  filterDrawer,
  highlight,
  setSortType: (_sortType: string) => {
    sortType.value = _sortType;
  },
});

const emit = defineEmits(['filter']);

const getAllSubCategories = () => {
  const allSubCategories: string[] = [];
  const traverseCategory = (cat: MountCategory) => {
    cat.subCategories.forEach((sub) => {
      allSubCategories.push(sub.name);
    });
  };
  categories.value.forEach(traverseCategory);
  return allSubCategories;
};

const drawerAction = (value: boolean) => {
  filterDrawer.value = value;
};

const clearFilters = () => {
  searchFilter.value = '';
  selectedRootCategories.value = [];
  selectedSubCategories.value = [];
  selectedMiscFilters.value = [];
  sortType.value = '';
};

watch(
  [selectedRootCategories, selectedSubCategories, selectedMiscFilters, searchFilter, sortType],
  () => {
    emit('filter', {
      search: searchFilter.value ?? '',
      rootCategories: selectedRootCategories.value ?? [],
      subCategories: selectedSubCategories.value ?? [],
      miscFilters: selectedMiscFilters.value ?? [],
      sort: sortType.value ?? '',
    });
  },
);
watch(highlight, (value) => {
  if (filterRef.value) {
    filterRef.value.highlight = value;
  }
});
watch(filterDrawer, (value) => {
  if (filterRef.value) {
    filterRef.value.filterDrawer = value;
  }
});

onMounted(() => {
  categories.value = collectablesStore.mountCategories;
  rootCategories.value = collectablesStore.mountCategories.map((cat) => cat.name);
  subCategories.value = getAllSubCategories();
});
</script>

<template>
  <Filter ref="filterRef" @drawer="drawerAction">
    <template #title>
      <v-text-field
        ref="searchField"
        placeholder="Search for a mount"
        hide-details
        variant="underlined"
        v-model="searchFilter"
        focused
        clearable
        id="search-field"
      >
        <template v-slot:append-inner>
          <v-icon>mdi-magnify</v-icon>
        </template>
      </v-text-field>
    </template>
    <template #content>
      <v-row justify="start">
        <v-col cols="12" class="d-flex justify-space-between">
          <v-label class="f-size-large align"
            ><v-icon size="small" icon="mdi-filter" />Filters</v-label
          >
          <ClearFilterButton
            text="Clear all filters"
            :disabled="!filterNotEmpty"
            @clear="clearFilters"
          />
        </v-col>
        <v-divider />
        <!-- Base Categories -->
        <v-col class="filter-section">
          <v-label>Base Categories</v-label>
          <v-combobox
            chips
            multiple
            variant="underlined"
            :items="rootCategories"
            item-title="category"
            v-model="selectedRootCategories"
            clearable
          ></v-combobox>
        </v-col>
        <!-- Sub Categories -->
        <v-col cols="12" class="filter-section">
          <v-label>Sub Categories</v-label>
          <v-combobox
            chips
            multiple
            variant="underlined"
            :items="subCategories"
            item-title="category"
            v-model="selectedSubCategories"
            clearable
          ></v-combobox>
        </v-col>
        <!-- Misc Filters -->
        <v-col cols="12" class="filter-section">
          <v-label>Misc</v-label>
          <v-combobox
            chips
            multiple
            variant="underlined"
            :items="miscFilters"
            item-title="category"
            v-model="selectedMiscFilters"
            clearable
          ></v-combobox>
        </v-col>
      </v-row>
    </template>
  </Filter>
</template>

<style scoped lang="scss">
.filter-section {
  position: relative;
}
</style>
