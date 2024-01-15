<script setup lang="ts">
import { ref } from 'vue';

const modalOpen = ref(false);

defineProps<{
  title: string;
  text: string;
  confirmText: string;
  cancelText: string;
}>();
defineExpose({
  open: () => {
    modalOpen.value = true;
  },
});
const emit = defineEmits(['confirm', 'cancel']);

const action = (type: 'confirm' | 'cancel') => {
  emit(type);
  modalOpen.value = false;
};
</script>

<template>
  <v-dialog v-model="modalOpen" width="auto">
    <v-card>
      <v-card-title>{{ $props.title }}</v-card-title>
      <v-card-text>
        <p>{{ $props.text }}</p>
      </v-card-text>
      <v-card-actions class="justify-end">
        <v-btn variant="text" @click="() => action('cancel')">{{ $props.cancelText }}</v-btn>
        <v-btn variant="text" @click="() => action('confirm')" color="primary">{{
          $props.confirmText
        }}</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<style scoped lang="scss"></style>
