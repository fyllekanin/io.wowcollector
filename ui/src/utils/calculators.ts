import type { AchievementInformation } from '@/types/collections/achievements';
import type { MountInformation } from '@/types/collections/mounts';

export const calculateCollectedPercentage = (
  collectables: MountInformation[] | AchievementInformation[],
) => {
  const collected = collectables.filter((collectable) => collectable.isCollected);
  const percentage = Math.round((collected.length / collectables.length) * 100);
  return isNaN(percentage) ? 0 : percentage;
};
