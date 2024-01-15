<script setup lang="ts">
import Header from '@/components/Header.vue';
</script>

<template>
  <div class="center-container">
    <Header title="Not found" />
  </div>
</template>

<style scoped lang="scss">
.center-container {
  @media (max-width: 600px) {
    margin-top: 5rem;
  }

  margin-top: 2rem;
  display: grid;
  place-items: center;
}
</style>
