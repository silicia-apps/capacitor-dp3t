import { IStatus } from "./models";

declare module "@capacitor/core" {
  interface PluginRegistry {
    Dp3tPlugin: Dp3tPlugin;
  }
}

export interface Dp3tPlugin {
  init(options: {}): void;
  start(options: {}): void;
  stop(options: {}): void;
  sync(options: {}): void;
  getStatus(options: {}): Promise<IStatus>;
  askForDisableBatteryOptimizer(options: {}): void;
  sendIAmInfected(options: {}): void;
}


