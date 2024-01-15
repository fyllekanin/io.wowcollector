import { useLoadingStore, useTabStore } from '@/stores';

export const before404 = () => {
  const loadingStore = useLoadingStore();
  const tabStore = useTabStore();
  tabStore.save(null);
  loadingStore.save(false);
};
