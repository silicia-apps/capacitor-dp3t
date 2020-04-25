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

  async start(): Promise<void> {
    console.log('Start');
  }

  async stop(): Promise<void> {
    console.log('Stop');
  }

  async askForDisableBatteryOptimizer() {}
  async askForActivateBluetooth() {}
  async askForGeolocationPermission() {}

  async getStatus(): Promise<IStatus> {
    console.log('get Status');
    return { 
      advertising: false,
      isActive: false,
      receiving: false,
      lastSyncUpdate: new Date().valueOf(),
      wasContactExposed: false,
      numberOfContacts: 0,
      errors: ''
    };
  }
}

const Dp3tPlugin = new Dp3tPluginWeb();

// export { Dp3tPlugin };

import { registerWebPlugin } from '@capacitor/core';
registerWebPlugin(Dp3tPlugin);
