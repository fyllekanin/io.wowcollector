import { ref, onUnmounted, onBeforeMount } from 'vue';
import type { App } from 'vue';

const isTablet = ref(window.innerWidth < 1000);
const updateIsTablet = () => {
  isTablet.value = window.innerWidth < 1000;
};

window.addEventListener('resize', updateIsTablet);

onBeforeMount(() => {
  updateIsTablet(); // initial update
});

onUnmounted(() => {
  window.removeEventListener('resize', updateIsTablet);
});

export default {
  install: (app: App) => {
    app.provide('$isTablet', isTablet);
  },
};
