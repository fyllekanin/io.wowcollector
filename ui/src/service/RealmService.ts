import { get } from '@/service/HttpService';
import type { RealmsRegionResponse } from '@/types/realms';

export default class RealmService {
  static async getRealms() {
    try {
      const response = await get<RealmsRegionResponse>({
        url: '/api/v1/battle-net/realms-regions',
      });

      return response.data;
    } catch (error) {
      console.error(error);
      return null;
    }
  }
}
