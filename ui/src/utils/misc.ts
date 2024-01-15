import type { TooltipInfo } from '@/types/misc';
import { MountDetailMap } from '@/constants/mounts';

export const upperCaseFirst = (str: string) => {
  return str.charAt(0).toUpperCase() + str.slice(1);
};

export const parseMountInfo = (htmlString: string): TooltipInfo => {
  const parser = new DOMParser();
  const html = parser.parseFromString(htmlString, 'text/html');
  const yellowElements = Array.from(html.querySelectorAll("[style='color: #FFD200']"));

  const name = html.querySelector('.q4')?.textContent || null;
  const itemLevel =
    parseInt(html.querySelector('.whtt-ilvl')?.textContent?.split(' ')[2] || '0', 10) || null;
  const bindType = html.querySelector('.q0')?.textContent?.trim() || null;

  const raceRequirement =
    html.querySelector('.wowhead-tooltip-item-races')?.textContent?.replace('Requires ', '') ||
    null;
  const use = html.querySelectorAll('.q2')[0]?.textContent?.replace('Use: ', '') || null;
  const summon = html.querySelectorAll('.q2')[1]?.textContent || null;
  const levelText = html
    .querySelector('td')
    ?.textContent?.match(/Requires level (\d+) to (\d+) \((\d+)\)/);
  const minLevel = levelText ? parseInt(levelText[1]) : null;
  const maxLevel = levelText ? parseInt(levelText[2]) : null;
  const currentLevel = levelText ? parseInt(levelText[3]) : null;
  const requirement = html.querySelector('.q1')?.textContent || null;
  const cost = parseInt(html.querySelectorAll('.moneygold')[1]?.textContent || '0') || null;
  const dropChanceText = html
    .querySelector('.whtt-dropchance')
    ?.textContent?.match(/Drop Chance: (.+)%/);
  const dropChance = dropChanceText ? `${parseFloat(dropChanceText[1])}%` : null;

  let drop = null;
  let vendor = null;
  let location = null;
  let quest = null;
  let zone = null;
  let achievement = null;
  let category = null;

  yellowElements.forEach((element) => {
    const value = element.nextSibling?.textContent?.trim();
    const key = element.textContent?.trim();
    switch (key) {
      case 'Drop:':
        drop = value;
        break;
      case 'Vendor:':
        vendor = value;
        break;
      case 'Location:':
        location = value;
        break;
      case 'Quest:':
        quest = value;
        break;
      case 'Zone:':
        zone = value;
        break;
      case 'Achievement:':
        achievement = value;
        break;
      case 'Category:':
        category = value;
        break;
    }
  });

  const result = Object.fromEntries(
    Object.entries({
      name,
      itemLevel,
      bindType,
      raceRequirement,
      use,
      summon,
      minLevel,
      maxLevel,
      currentLevel,
      requirement,
      drop,
      vendor,
      location,
      cost,
      quest,
      zone,
      achievement,
      category,
      dropChance,
    })
      .filter(([, value]) => value !== null)
      .map(([key, value]) => [MountDetailMap[key as keyof typeof MountDetailMap], value]),
  );

  return result as unknown as TooltipInfo;
};

let debounceTimer: NodeJS.Timeout | null = null;
export const debounce = (func: Function, delay: number) => {
  return (...args: any[]) => {
    if (debounceTimer) {
      clearTimeout(debounceTimer);
    }
    debounceTimer = setTimeout(() => func(...args), delay);
  };
};
