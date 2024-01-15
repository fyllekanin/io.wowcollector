import type { CharacterInformation } from './character';

export interface AuthenticatedUser {
  battleTag: string;
  defaultCharacter: CharacterInformation;
  accessToken: string;
  refreshToken: string;
}
