import { get } from '@/service/HttpService';
import type { WoWHeadMountInformation } from '@/types/collections/mounts';

export default class MountService {
  static async getMountInformation(mountId: number): Promise<WoWHeadMountInformation | null> {
    try {
      const response = await get<WoWHeadMountInformation>({
        url: `https://nether.wowhead.com/tooltip/mount/${mountId}?dataEnv=1&locale=0`,
      });

      return response.data;
    } catch (error) {
      console.error(error);
      return null;
    }
  }
}
