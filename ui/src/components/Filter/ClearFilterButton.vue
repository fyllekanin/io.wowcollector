<script setup lang="ts">
defineProps({
  text: {
    type: String,
    required: true,
  },
  disabled: {
    type: Boolean,
    default: false,
  },
});

defineEmits(['clear']);
</script>

<template>
  <v-tooltip :text="$props.text" location="top">
    <template v-slot:activator="{ props }">
      <v-btn
        v-bind="props"
        class="clear-filter-button"
        variant="plain"
        size="x-small"
        icon
        :disabled="$props.disabled"
        @click="$emit('clear')"
        ><v-icon size="x-large">mdi-filter-off</v-icon></v-btn
      >
    </template>
  </v-tooltip>
</template>

<style scoped lang="scss"></style>
