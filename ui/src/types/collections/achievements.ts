export interface AchievementCategory {
  name: string;
  achievements: AchievementInformation[];
  subCategories: AchievementCategory[];
  displayOrder: number;
}

export interface AchievementInformation {
  name: string;
  description: string;
  id: number;
  points: number;
  icon: string;
  displayOrder: number;
  isCollected: boolean;
  collected: boolean;
}
