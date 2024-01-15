<script setup lang="ts">
import Header from '@/components/Header.vue';
import LandingLayout from '@/layouts/Landing/LandingLayout.vue';
</script>

<template>
  <div class="center-container">
    <Header title="Explore our services" />
    <LandingLayout />
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
