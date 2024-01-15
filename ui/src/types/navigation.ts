import type { Icons } from '@/constants';

export interface Tab {
  route: string;
  alias: string;
  icon: Icons;
  show: boolean;
  isMenu: boolean;
  children?: undefined;
}
