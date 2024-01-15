import type { TooltipInfo } from '@/types/misc';

export const MountDetailMap: { [key in keyof TooltipInfo]: string } = {
  name: 'Name',
  itemLevel: 'Item Level',
  bindType: 'Bind Type',
  mountType: 'Mount Type',
  raceRequirement: 'Race Requirement',
  use: 'Use',
  summon: 'Summon',
  minLevel: 'Minimum Level',
  maxLevel: 'Maximum Level',
  currentLevel: 'Current Level',
  requirement: 'Requirement',
  sellPrice: 'Sell Price',
  lore: 'Lore',
  drop: 'Dropped By',
  vendor: 'Vendor',
  location: 'Location',
  cost: 'Cost',
  dropChance: 'Drop Chance',
  quest: 'Quest',
  zone: 'Zone',
  achievement: 'Achievement',
  category: 'Category',
};
