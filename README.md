# Capacitor Dp3t

!!! Sorry, i can't continue this work because i need a sponsoship from a government entity or public health organization for access to google/apple contact tracing api. !!!

Capacitor Dp3t is a native Dp3t-sdk bridge for IOS & Android. Now you can use this package as a [Ionic Capacitor](https://capacitor.ionicframework.com) Plugin in your App.

## Release Note:

### [v0.0.1]

- Implement Dp3t-sdk for Android.

### [v0.0.2]

- add request function for activate bluetooth
- add request functon for disable Battery Optmizer

### [v0.0.3]

- update depency to dp3t-sdk 0.2.6 (last version without google/apple contact tracking api)
- add function for send infect notification to backend
- add configuration options

### [v0.0.5]

- update capacitor dependencies

## Supported Platform

- [x] iOS (in roadmap)
- [x] Android (partial)

## Ionic Dp3t Demo App

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
## 📌 Configure Plugin

Open your Ionic app **capacitor.config.json** file and add this following code in plugins section.

N.B. this configuration is for official dp3t dev backend, you can install your own backend and use your configuration.

```json
"Dp3tPlugin": {
      "dev": true,
      "app_id": "your.app.id",
      "auth_code": "https://codegen-service-d.bag.admin.ch/",
      "config_url": "https://demo.dpppt.org/",
      "bucket_url": "https://demo.dpppt.org/",
      "report_url": "https://demo.dpppt.org/",
      "server_certificate": "sha256/YLh1dUR9y6Kja30RrAn7JKnbQG/uEtLMkBgFF2Fuihg=",
      "config_certificate": "LS0tLS1CRUdJTiBQVUJMSUMgS0VZLS0tLS0KTUZrd0V3WUhLb1pJemowQ0FRWUlLb1pJemowREFRY0RRZ0FFdkxXZHVFWThqcnA4aWNSNEpVSlJaU0JkOFh2UgphR2FLeUg2VlFnTXV2Zk1JcmxrNk92QmtKeHdhbUdNRnFWYW9zOW11di9rWGhZdjF1a1p1R2RjREJBPT0KLS0tLS1FTkQgUFVCTElDIEtFWS0tLS0tCg==",
      "bucket_public_key": "LS0tLS1CRUdJTiBQVUJMSUMgS0VZLS0tLS0KTUZrd0V3WUhLb1pJemowQ0FRWUlLb1pJemowREFRY0RRZ0FFdkxXZHVFWThqcnA4aWNSNEpVSlJaU0JkOFh2UgphR2FLeUg2VlFnTXV2Zk1JcmxrNk92QmtKeHdhbUdNRnFWYW9zOW11di9rWGhZdjF1a1p1R2RjREJBPT0KLS0tLS1FTkQgUFVCTElDIEtFWS0tLS0tCg=="
    }
```

## 📌 Initialize Plugin

Open your Ionic app **app.component.ts** file and add this following code.

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