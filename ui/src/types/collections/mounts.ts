export interface MountInformation {
  name: string;
  description: string;
  id: number;
  isCollected: boolean;
  creatureDisplay: string;
  icon: string;
}

export interface MountCategory {
  category: Category;
  name: string;
  mounts: MountInformation[];
  subCategories: SubCategory[];
  order: number;
}

export type Category = 'UNKNOWN' | 'CLASSIC';

export interface SubCategory {
  category: Category;
  name: string;
  mounts: MountInformation[];
  subCategories: SubCategory[];
  order: number;
}

export interface WoWHeadMountInformation {
  completion_category: string;
  icon: string;
  name: string;
  quality: number;
  spells: unknown[];
  tooltip: string;
}
