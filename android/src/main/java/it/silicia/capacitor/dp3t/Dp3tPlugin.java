package it.silicia.capacitor.dp3t;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.getcapacitor.JSObject;
import com.getcapacitor.NativePlugin;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;

import android.util.Log;

import org.dpppt.android.sdk.DP3T;
import org.dpppt.android.sdk.TracingStatus;

@NativePlugin()
public class Dp3tPlugin extends Plugin {

    private BroadcastReceiver sdkReceiver;

    @Override
    public void load() {
        String clientId = "org.dpppt.demo";
        sdkReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d("test","cambio dati");
                try {
                    JSObject ret = new JSObject();
                    TracingStatus tracingStatus = DP3T.getStatus(context);
                    ret.put("numberOfContacts", tracingStatus.getNumberOfHandshakes());
                    ret.put("advertising", tracingStatus.isAdvertising());
                    ret.put("receiving", tracingStatus.isReceiving());
                    ret.put("isReportAsExposed", tracingStatus.isReportedAsExposed());
                    ret.put("wasContactExposed", tracingStatus.wasContactExposed());
                    ret.put("lastSyncUpdate", tracingStatus.getLastSyncDate());
                    ret.put("isActive", DP3T.isStarted(context));
                    ret.put("errors", tracingStatus.getErrors());
                    Log.d("test", ret.getString("advertising"));
                    notifyListeners("Dp3tPluginUpdate", ret);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        this.getContext().registerReceiver(sdkReceiver, DP3T.getUpdateIntentFilter());
        try {
            Log.d("init dp3t","test");
            DP3T.init(this.getContext(), clientId, true);
            Log.d("init dp3t","done");
        } catch (Exception e) {
            Log.e("init dp3t", e.getLocalizedMessage());
        }
    }

    @PluginMethod()
    public void start(PluginCall call) {
        try {
            Log.d("start dp3t", "try");
            DP3T.start(getContext());
            Log.d("start dp3t", "done");
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
    public void getStatus(PluginCall call) {
        try {
            Log.d("test","get dati");
            JSObject ret = new JSObject();
            TracingStatus tracingStatus = DP3T.getStatus(getContext());
            ret.put("numberOfContacts", tracingStatus.getNumberOfHandshakes());
            ret.put("advertising", tracingStatus.isAdvertising());
            ret.put("receiving", tracingStatus.isReceiving());
            ret.put("isReportAsExposed", tracingStatus.isReportedAsExposed());
            ret.put("isActive", DP3T.isStarted(getContext()));
            ret.put("wasContactExposed", tracingStatus.wasContactExposed());
            ret.put("lastSyncUpdate", tracingStatus.getLastSyncDate());
            ret.put("errors", tracingStatus.getErrors());
            call.resolve(ret);
        } catch (Exception e) {
            e.printStackTrace();
            call.error(e.getMessage());
        }
    }

    @PluginMethod()
    public void sendIWasExposed(PluginCall call) {

    }

    @PluginMethod()
    public void clearData(PluginCall call) {
        // DP3T.clearData(getContext());
    }

}

