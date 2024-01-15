import { storeToRefs } from 'pinia';

import { Icons } from '.';

import { useAuthStore } from '@/stores';

export const getTabs = () => {
  const { isAuthenticated } = storeToRefs(useAuthStore());

  const tabs = [
    { route: '/', alias: 'home', icon: Icons.HOME, show: false, isMenu: false },
    {
      route: '/collectables',
      alias: 'collectables',
      icon: Icons.COLLECTABLES,
      isMenu: true,
      show: false,
      children: [
        { route: '/mounts', alias: 'mounts', icon: Icons.MOUNTS, show: true, isMenu: false },
        {
          route: '/achievements',
          alias: 'achievements',
          icon: Icons.ACHIEVEMENTS,
          show: true,
          isMenu: false,
        },
        // More child routes here
      ],
    },
    { route: '/search', alias: 'search', icon: Icons.SEARCH, show: true, isMenu: false },
    ...(isAuthenticated.value
      ? [{ route: '/me', alias: 'my account', icon: Icons.ACCOUNT, show: true, isMenu: false }]
      : []),
  ];

  return tabs;
};
