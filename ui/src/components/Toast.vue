<script lang="ts" setup>
import { ref } from 'vue';

import type { PropType } from 'vue';

const visible = ref(false);

const props = defineProps({
  text: {
    type: String,
    required: true,
  },
  timeout: {
    type: Number,
    default: 3000,
  },
  type: {
    type: String as PropType<'error' | 'success'>,
    enum: ['success', 'error'],
    default: 'success',
  },
});
defineExpose({
  show() {
    visible.value = true;

    setTimeout(() => {
      visible.value = false;
    }, props.timeout);
  },
});
</script>

<template>
  <v-snackbar v-model="visible" :color="$props.type" variant="tonal">
    {{ text }}
    <template v-slot:actions>
      <v-btn color="background" variant="elevated" @click="visible = false">
        <v-icon icon="mdi-close" />
      </v-btn>
    </template>
  </v-snackbar>
</template>

<style lang="scss" scoped></style>
