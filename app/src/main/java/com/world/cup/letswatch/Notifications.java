package com.world.cup.letswatch;

import android.app.Application;

import com.onesignal.OneSignal;

public class Notifications  extends Application {
    private static final String ONESIGNAL_APP_ID = "e6a7c1bb-5ad5-4b14-8f71-550b2889a728";

    @Override
    public void onCreate() {
        super.onCreate();
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);
        OneSignal.initWithContext(this);
        OneSignal.setAppId(ONESIGNAL_APP_ID);
    }
}
