import { get, put } from '@/service/HttpService';

import type { CharacterInformation, Me } from '@/types/character';

export default class CharacterService {
  static async getCharacterInformation(character: Partial<CharacterInformation>) {
    const { realm, name, region } = character;
    if (!realm || !name || !region) return null;

    try {
      const response = await get<CharacterInformation>({
        url: `/api/v1/battle-net/character/${region.toLowerCase()}/${realm.toLowerCase()}/${name.toLowerCase()}`,
      });

      return response.data;
    } catch (error) {
      console.error(error);
      return null;
    }
  }

  static async me() {
    try {
      const response = await get<Me | null>({
        url: '/api/v1/user/me',
      });

      return response.data;
    } catch (error) {
      console.error(error);
      return null;
    }
  }

  static async updateDefaultCharacter(defaultCharacterId: number) {
    try {
      const response = await put<unknown, '' | null>({
        url: '/api/v1/user/update',
        body: {
          defaultCharacterId,
        },
      });

      return response.status === 200;
    } catch (error) {
      console.error(error);
      return null;
    }
  }
}
