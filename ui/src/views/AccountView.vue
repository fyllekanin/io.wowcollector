<script setup lang="ts">
import AccountLayout from '@/layouts/Account/AccountLayout.vue';
</script>

<template>
  <div class="center-container">
    <AccountLayout />
  </div>
</template>

<style scoped lang="scss">
.center-container {
  @media (max-width: 600px) {
    margin-top: 3rem;
  }

  display: grid;
  place-items: center;
}
</style>
