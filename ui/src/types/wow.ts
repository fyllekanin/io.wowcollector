export type Faction = 'ALLIANCE' | 'HORDE';

export type Class =
  | 'Death Knight'
  | 'Demon Hunter'
  | 'Druid'
  | 'Evoker'
  | 'Hunter'
  | 'Mage'
  | 'Monk'
  | 'Paladin'
  | 'Priest'
  | 'Rogue'
  | 'Shaman'
  | 'Warlock'
  | 'Warrior';
export type Spec = 'TANK' | 'HEALER' | 'DPS';
export type Gender = 'MALE' | 'FEMALE';

export interface Achievement {
  id: number;
  name: string;
  description: string;
  icon: string;
  completedAt: number;
}
