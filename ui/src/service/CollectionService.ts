import { get } from '@/service/HttpService';

import type { CharacterInformation, Collection } from '@/types/character';
import type { MountInformationResponse, AchievementInformationResponse } from '@/types/collections';

export default class CollectionService {
  static async getCollectionInformation<
    T extends MountInformationResponse | AchievementInformationResponse,
  >(character: Partial<CharacterInformation>, collection: Collection) {
    const { realm, name, region } = character;
    if (!realm || !name || !region || !collection) return null;

    try {
      const response = await get<T>({
        url: `/api/v1/collections/${region.toLowerCase()}/${realm.toLowerCase()}/${name.toLowerCase()}/${collection}`,
      });

      return response.data;
    } catch (error) {
      console.error(error);
      return null;
    }
  }
}
