import { get } from '@/service/HttpService';

import type { AuthenticatedUser } from '@/types/auth';

export default class AuthService {
  static async login(code: string) {
    try {
      const response = await get<AuthenticatedUser | null>({
        url: `/api/v1/auth/oauth?code=${code}`,
      });

      return response.data;
    } catch (error) {
      console.error(error);
      return null;
    }
  }

  static async refresh(refreshToken: string) {
    try {
      const response = await get<unknown>({
        url: `/api/v1/auth/refresh`,
        config: {
          headers: {
            RefreshToken: refreshToken,
          },
        },
      });

      return response.data;
    } catch (error) {
      console.error(error);
      return null;
    }
  }

  static async initial() {
    try {
      const response = await get<unknown>({
        url: `/api/v1/user/initial`,
      });

      return response.data;
    } catch (error) {
      console.error(error);
      return null;
    }
  }
}
