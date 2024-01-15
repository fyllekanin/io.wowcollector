import type { AchievementCategory } from './collections/achievements';
import type { MountCategory } from './collections/mounts';

export interface MountInformationResponse {
  mountCategories: MountCategory[];
}

export interface AchievementInformationResponse {
  achievementCategories: AchievementCategory[];
}

export type CollectionInformationResponse =
  | MountInformationResponse
  | AchievementInformationResponse;
