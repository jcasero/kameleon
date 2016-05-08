package com.tekihub.kameleon.watchtheme.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.tekihub.kameleon.watchtheme.WatchThemeManager;
import com.tekihub.kameleon.watchtheme.di.WatchThemeScope;

import javax.inject.Inject;

@WatchThemeScope
public class ScreenStatusReceiver extends BroadcastReceiver {

    private WatchThemeManager watchThemeManager;

    @Inject public ScreenStatusReceiver(WatchThemeManager watchThemeManager) {
        this.watchThemeManager = watchThemeManager;
    }

    @Override public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
            watchThemeManager.start();
        } else if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            watchThemeManager.stop();
        }
    }

    public static IntentFilter getIntentFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_SCREEN_ON);
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        return intentFilter;
    }
}
