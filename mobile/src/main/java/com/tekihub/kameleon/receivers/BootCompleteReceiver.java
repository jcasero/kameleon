package com.tekihub.kameleon.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.tekihub.kameleon.watchtheme.AppThemeService;

public class BootCompleteReceiver extends BroadcastReceiver {

    @Override public void onReceive(Context context, Intent intent) {
        AppThemeService.start(context);
    }
}
