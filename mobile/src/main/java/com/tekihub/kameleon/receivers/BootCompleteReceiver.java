package com.tekihub.kameleon.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.tekihub.kameleon.KameleonService;

public class BootCompleteReceiver extends BroadcastReceiver {

    @Override public void onReceive(Context context, Intent intent) {
        KameleonService.start(context);
    }
}
