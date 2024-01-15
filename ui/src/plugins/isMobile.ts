import { ref, onUnmounted, onBeforeMount } from 'vue';
import type { App } from 'vue';

const isMobile = ref(window.innerWidth < 480);
const updateIsMobile = () => {
  isMobile.value = window.innerWidth < 480;
};

window.addEventListener('resize', updateIsMobile);

onBeforeMount(() => {
  updateIsMobile(); // initial update
});

onUnmounted(() => {
  window.removeEventListener('resize', updateIsMobile);
});

export default {
  install: (app: App) => {
    app.provide('$isMobile', isMobile);
  },
};
