# Capacitor Dp3t

Capacitor Dp3t is a native Dp3t-sdk bridge for IOS & Android. Now you can use this package as a [Ionic Capacitor](https://capacitor.ionicframework.com) Plugin in your App.

## Release Note:

### [v0.0.1]

- Implement Dp3t-sdk for Android.

### [v0.0.2]

- add request function for activate bluetooth
- add request functon for disable Battery Optmizer

## Supported Platform

- [x] iOS (in roadmap)
- [x] Android (partial)

## Dp3t Demo App

In a few days

## Installation

Use plugin in your app.

```console
 npm install --save @silicia/capacitor-dp3t
```

### Register Plugin to Capacitor Android

Open your Ionic Capacitor App in Android Studio, Now open **MainActivity.java** of your app and Register Plugin to Capacitor Plugins.

```java
// Other imports...
import it.silicia.capacitor.dp3t.Dp3tPlugin;

public class MainActivity extends BridgeActivity {
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    this.init(savedInstanceState, new ArrayList<Class<? extends Plugin>>() {{

      add(Dp3tPlugin.class);  // Add DP3t plugin as a Capacitor Plugin

    }});
  }
}
```

## 📌 Initialize Plugin

Open your Ionic app **app.component.ts** file and add this folloing code.

```typescript
import 'capacitor-dp3t-plugin';
import {Plugins} from '@capacitor/core';
const { Dp3tPlugin } = Plugins;

@Component({
  selector: "app-root",
  templateUrl: "app.component.html",
  styleUrls: ["app.component.scss"]
})
export class AppComponent {
  constructor() {
    // Initialize Plugin for your Application
    // TO-DO (at this moment the plugin do automatic initialization of sdk with demo appId)
    // 
    // Listen for state change
    Dp3tPlugin.addListener('Dp3tPluginUpdate', (info: IStatus) => {
      console.log('we have a change in the state');
      console.log(JSON.stringify(info));
    });

    // Start Process
    Dp3tPlugin.start();

    // Stop Process
    Dp3tPlugin.stop();

  }
}
```

## Contributing

- 🌟 Star this repository
- 📋 Open issue for feature requests

## Roadmap

- [dp3t-sdk-android] (https://github.com/DP-3T/dp3t-sdk-android)

- [dp3t-sdk-ios] (https://github.com/DP-3T/dp3t-sdk-ios)

- [Capacitor Plugins](https://capacitor.ionicframework.com/docs/plugins/)

- [IOS](https://capacitor.ionicframework.com/docs/plugins/ios/)

- [Android](https://capacitor.ionicframework.com/docs/plugins/android/)

## License

Capacitor Dp3t is [MPL 2.0 licensed](./LICENSE).