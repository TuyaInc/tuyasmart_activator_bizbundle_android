package com.tuya.smart.config.bizbundle;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.tuya.smart.android.common.utils.L;
import com.tuya.smart.home.sdk.TuyaHomeSdk;
import com.tuya.smart.sdk.TuyaSdk;
import com.tuya.smart.wrapper.api.TuyaWrapper;


public class TuyaSmartApp extends Application {

    private static final String TAG = "TuyaSmartApp";

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        L.d(TAG, "onCreate " + getProcessName(this));
        L.setSendLogOn(true);
        TuyaWrapper.init(this);
        TuyaSdk.init(this);
        TuyaHomeSdk.setDebugMode(true);
        Fresco.initialize(this);
    }

    public static String getProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager mActivityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager
                .getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return "";
    }

    private static Context context;

    public static Context getAppContext() {
        return context;
    }


}
