import { ref, onUnmounted, onBeforeMount } from 'vue';
import type { App } from 'vue';

const isSmallTablet = ref(window.innerWidth < 600);
const updateisSmallTablet = () => {
  isSmallTablet.value = window.innerWidth < 600;
};

window.addEventListener('resize', updateisSmallTablet);

onBeforeMount(() => {
  updateisSmallTablet(); // initial update
});

onUnmounted(() => {
  window.removeEventListener('resize', updateisSmallTablet);
});

export default {
  install: (app: App) => {
    app.provide('$isSmallTablet', isSmallTablet);
  },
};
