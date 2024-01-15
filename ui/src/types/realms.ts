export interface RealmInformation {
  id: number;
  name: string;
  slug: string;
  region: string;
}

export type Region = 'eu' | 'us' | 'kr' | 'tw';

export interface RealmsRegionResponse {
  realms: RealmInformation[];
  regions: Region[];
}
