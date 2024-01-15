import { h } from 'vue';

import type { IconSet, IconProps } from 'vuetify';

import LogoIcon from '@/components/icons/Logo.vue';
import BattlenetIcon from '@/components/icons/Battlenet.vue';

import TankIcon from '@/components/icons/wow/spec/Tank.vue';
import HealerIcon from '@/components/icons/wow/spec/Healer.vue';
import DPSIcon from '@/components/icons/wow/spec/DPS.vue';

import DeathKnightIcon from '@/components/icons/wow/classes/DeathKnight.vue';
import DemonHunterIcon from '@/components/icons/wow/classes/DemonHunter.vue';
import DruidIcon from '@/components/icons/wow/classes/Druid.vue';
import EvokerIcon from '@/components/icons/wow/classes/Evoker.vue';
import HunterIcon from '@/components/icons/wow/classes/Hunter.vue';
import MageIcon from '@/components/icons/wow/classes/Mage.vue';
import MonkIcon from '@/components/icons/wow/classes/Monk.vue';
import PaladinIcon from '@/components/icons/wow/classes/Paladin.vue';
import PriestIcon from '@/components/icons/wow/classes/Priest.vue';
import RogueIcon from '@/components/icons/wow/classes/Rogue.vue';
import ShamanIcon from '@/components/icons/wow/classes/Shaman.vue';
import WarlockIcon from '@/components/icons/wow/classes/Warlock.vue';
import WarriorIcon from '@/components/icons/wow/classes/Warrior.vue';

const customSvgNameToComponent: any = {
  logo: LogoIcon,
  battlenet: BattlenetIcon,
  tank: TankIcon,
  healer: HealerIcon,
  dps: DPSIcon,
  deathKnight: DeathKnightIcon,
  demonHunter: DemonHunterIcon,
  druid: DruidIcon,
  evoker: EvokerIcon,
  hunter: HunterIcon,
  mage: MageIcon,
  monk: MonkIcon,
  paladin: PaladinIcon,
  priest: PriestIcon,
  rogue: RogueIcon,
  shaman: ShamanIcon,
  warlock: WarlockIcon,
  warrior: WarriorIcon,
};

export const customSVGs: IconSet = {
  // @ts-ignore
  component: (props: IconProps) => h(customSvgNameToComponent[props.icon]),
};
