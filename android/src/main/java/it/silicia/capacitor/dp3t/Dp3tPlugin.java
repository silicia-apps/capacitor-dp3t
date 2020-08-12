package it.silicia.capacitor.dp3t;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.getcapacitor.Config;
import com.getcapacitor.JSObject;
import com.getcapacitor.NativePlugin;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;

import android.net.Uri;
import android.provider.Settings;
import android.util.Log;

import okhttp3.CertificatePinner;

import org.dpppt.android.sdk.DP3T;
import org.dpppt.android.sdk.TracingStatus;
import org.dpppt.android.sdk.backend.models.ApplicationInfo;
import org.dpppt.android.sdk.backend.models.ExposeeAuthMethodJson;
import org.dpppt.android.sdk.internal.backend.BackendBucketRepository;
import org.dpppt.android.sdk.util.SignatureUtil;
import org.dpppt.android.sdk.backend.ResponseCallback;

import java.security.PublicKey;
import java.util.Calendar;

@NativePlugin()
public class Dp3tPlugin extends Plugin {

    public static final String CONFIG_KEY_PREFIX = "plugins.Dp3tPlugin.";
    private static final String PLUGIN_TAG = "Dp3tPlugin";

    private BroadcastReceiver sdkReceiver;

    public static void initDP3T(Context context) {
        if (Config.getBoolean("dev",false)) {
            BackendBucketRepository.BATCH_LENGTH = 5 * 60 * 1000L;
        }
        String app_id = Config.getString(CONFIG_KEY_PREFIX+"app_id", "it.silicia.covit");
        String report_url = Config.getString(CONFIG_KEY_PREFIX+"report_url");
        String bucket_url = Config.getString((CONFIG_KEY_PREFIX+"bucket_url"));
        String bucket_public_key = Config.getString((CONFIG_KEY_PREFIX+"bucket_public_key"));
        String server_certificate = Config.getString(CONFIG_KEY_PREFIX+"server_certificate");
        PublicKey signaturePublicKey = SignatureUtil.getPublicKeyFromBase64OrThrow(bucket_public_key);
        try {
            Log.d("[" + PLUGIN_TAG + "]", "init dp3t");
            DP3T.init(context, new ApplicationInfo(app_id, report_url, bucket_url), signaturePublicKey);
            DP3T.setCertificatePinner(new CertificatePinner.Builder().add(server_certificate).build());
        } catch (Exception e) {
            Log.e("[" + PLUGIN_TAG + "]", e.getMessage());
        }
    }

    @Override
    public void load() {
        this.initDP3T(getContext());
        sdkReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d("[" + PLUGIN_TAG + "]","Dp3tUpdate");
                try {
                    JSObject ret = new JSObject();
                    TracingStatus tracingStatus = DP3T.getStatus(context);
                    ret.put("infectionStatus", tracingStatus.getInfectionStatus());
                    ret.put("exposureDays", tracingStatus.getExposureDays());
                    ret.put("lastSyncUpdate", tracingStatus.getLastSyncDate());
                    ret.put("isActive", DP3T.isStarted(getContext()));
                    ret.put("errors", tracingStatus.getErrors());
                    notifyListeners("Dp3tPluginUpdate", ret);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        this.getContext().registerReceiver(sdkReceiver, DP3T.getUpdateIntentFilter());
    }

    @PluginMethod()
    public void start(PluginCall call) {
        try {
            DP3T.start(getContext());
            call.success();
        } catch (Exception e) {
            e.printStackTrace();
            call.error(e.getMessage());
        }
    }

    @PluginMethod()   
    public void stop(PluginCall call) {
        try {
            DP3T.stop(getContext());
            call.success();
        } catch (Exception e) {
            e.printStackTrace();
            call.error(e.getMessage());
        }
    }

    @PluginMethod()
    public void sync(PluginCall call) {
        try {
            DP3T.sync(getContext());
            call.success();
        } catch (Exception e) {
            e.printStackTrace();
            call.error(e.getMessage());
        }
    }

    @PluginMethod()
    public void getStatus(PluginCall call) {
        try {
            Log.d("[" + PLUGIN_TAG + "]","get dati");
            JSObject ret = new JSObject();
            TracingStatus tracingStatus = DP3T.getStatus(this.getContext());
            ret.put("infectionStatus", tracingStatus.getInfectionStatus());
            ret.put("exposureDays", tracingStatus.getExposureDays());
            ret.put("lastSyncUpdate", tracingStatus.getLastSyncDate());
            ret.put("isActive", DP3T.isStarted(getContext()));
            ret.put("errors", tracingStatus.getErrors());
            call.resolve(ret);
        } catch (Exception e) {
            e.printStackTrace();
            call.error(e.getMessage());
        }
    }

    @PluginMethod()
    public void askForDisableBatteryOptimizer(PluginCall call) {
        try {
            this.getContext().startActivity(new Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS, Uri.parse("package:" + getContext().getPackageName())));
        } catch (Exception e) {
            e.printStackTrace();
            call.error(e.getMessage());
        }
    }

    @PluginMethod()
    public void askForActivateBluetooth(PluginCall call) {
        try {
            this.getContext().startActivity(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE));
            call.success();
        } catch (Exception e) {
            e.printStackTrace();
            call.error(e.getMessage());
        }
    }

    @PluginMethod()
    public void sendIAmInfected(PluginCall call) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(calendar.DATE, -14);
        String authCode = call.getString("authCode", "0123456789");
        DP3T.sendIAmInfected(getContext(), calendar.getTime(),
                new ExposeeAuthMethodJson(authCode), new ResponseCallback<Void>() {
                    @Override
                    public void onSuccess(Void response) {
                        call.success();
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        call.error(throwable.getMessage());
                    }
                });
    }

    @PluginMethod()
    public void clearData(PluginCall call) {
        try {
            DP3T.stop(getContext());
            DP3T.clearData(getContext(), () -> {
                Log.d("[" + PLUGIN_TAG + "]", "Sdk Resetted");
            });
            this.initDP3T(getContext());
            call.success();
        } catch (Exception e) {
            e.printStackTrace();
            call.error(e.getMessage());
        }
    }
}
