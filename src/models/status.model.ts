
export interface IStatus {
  isActive: boolean;
  numberOfContacts: number,
  advertising: boolean,
  receiving: boolean,
  infectionStatus: 'HEALTHY' | 'EXPOSED' | 'INFECTED',
  matchedContacts: any[], //TODO: change this with a model
  lastSyncUpdate: number,
  errors: string
}