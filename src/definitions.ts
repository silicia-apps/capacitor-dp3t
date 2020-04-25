import { IStatus } from "./models";

declare module "@capacitor/core" {
  interface PluginRegistry {
    Dp3tPlugin: Dp3tPlugin;
  }
}

export interface Dp3tPlugin {
  start(options: {}): void;
  stop(options: {}): void;
  getStatus(options: {}): Promise<IStatus>;
  askForDisableBatteryOptimizer(options: {}): void;
  askForActivateBluetooth(options: {}): void;
  askForGeolocationPermission(options: {}): void;
}


