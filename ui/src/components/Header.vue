<script setup lang="ts">
defineProps({
  title: {
    type: String,
    required: true,
  },
  description: {
    type: String,
  },
  image: {
    type: String,
  },
});
</script>

<template>
  <header>
    <div class="header-container">
      <div class="header-content">
        <h1 class="f-size-header-xl">{{ title }}</h1>
        <p v-if="description">{{ description }}</p>
      </div>
      <div v-if="image" class="header-image">
        <img :src="image" alt="header image" />
      </div>
    </div>
  </header>
</template>

<style scoped lang="scss">
header {
  max-width: 1200px;

  @media (max-width: 480px) {
    font-size: $font-small;
  }
}
.header-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 2rem;
  padding-left: 10px;
  padding-right: 10px;
}
</style>
