import { WebPlugin } from '@capacitor/core';
import { Dp3tPlugin } from './definitions';
import { IStatus } from './models';

export class Dp3tPluginWeb extends WebPlugin implements Dp3tPlugin {
  constructor() {
    super({
      name: 'Dp3tPlugin',
      platforms: ['web']
    });
  }

  async init() {
    console.log('[dp3t] Init');
  }

  async sync() {
    console.log('[dp3t] sync');
  }

  async start(): Promise<void> {
    console.log('[dp3t] Start');
  }

  async stop(): Promise<void> {
    console.log('[dp3t] Stop');
  }

  async askForDisableBatteryOptimizer() {
    console.log('[dp3t] Ask for Battery Optmizer Status');
  }
  
  async getStatus(): Promise<IStatus> {
    console.log('[dp3t] get Status');
    return { 
      advertising: false,
      isActive: false,
      receiving: false,
      lastSyncUpdate: new Date().valueOf(),
      matchedContacts: [],
      infectionStatus: 'HEALTHY',
      numberOfContacts: 0,
      errors: '[]'
    };
  }

  async sendIAmInfected(): Promise<void> {
    
  }
}



const Dp3tPlugin = new Dp3tPluginWeb();

// export { Dp3tPlugin };

import { registerWebPlugin } from '@capacitor/core';
registerWebPlugin(Dp3tPlugin);
