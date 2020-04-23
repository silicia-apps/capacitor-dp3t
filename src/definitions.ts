import { IStatus } from "./models";

declare module "@capacitor/core" {
  interface PluginRegistry {
    Dp3tPlugin: Dp3tPlugin;
  }
}

export interface Dp3tPlugin {
  start(options: {}): Promise<void>;
  stop(options: {}): Promise<void>;
  getStatus(options: {}): Promise<IStatus>;
}


