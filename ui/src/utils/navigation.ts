import router from '@/router';

import { useTabStore } from '@/stores';
import { clearCharacter } from '.';

export const goTo = (route: string) => {
  const tabStore = useTabStore();
  tabStore.save(route);
  router.push({ path: route }).catch(() => {});
};

export const goToExternal = (url: string) => {
  window.open(url, '_blank');
};

export const newSearch = () => {
  const tabStore = useTabStore();
  clearCharacter({
    store: true,
    localStorage: true,
  });
  tabStore.save('/search');
  router.push({ path: '/search' }).catch(() => {});
};

export const isAlreadyOnPath = (path: string) => {
  return router.currentRoute.value.path.includes(path);
};

export const scrollToTop = () => {
  window.scrollTo({ top: 0, behavior: 'smooth' });
};
