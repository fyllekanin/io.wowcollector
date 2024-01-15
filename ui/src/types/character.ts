import type { Achievement, Class, Faction, Gender } from './wow';

export interface CharacterInformation {
  id: number;
  name: string;
  realm: string;
  region: string;
  level: number;
  faction: Faction;
  media: CharacterMedia;
  clazz: Class;
  race: string;
  gender: Gender;
  specialization: string;
}

export interface CharacterMedia {
  avatar: string;
  inset: string;
  mainRaw: string;
}

export interface Me {
  latestAchievements: Achievement[];
  characters: CharacterInformation[];
}

export type Collection = 'mounts' | 'achievements';
