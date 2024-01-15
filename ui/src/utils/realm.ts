import { useRealmStore } from '@/stores';

export const getRealmName = (slug: string) => {
  const realmStore = useRealmStore();
  return realmStore.realms.find((realm) => realm.slug === slug)?.name;
};
