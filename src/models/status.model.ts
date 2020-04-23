export interface IStatus {
  isActive: boolean;
  numberOfContacts: number,
  advertising: boolean,
  receiving: boolean,
  wasContactExposed: boolean,
  lastSyncUpdate: number,
  errors: string[]
}