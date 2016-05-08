package com.tekihub.kameleon;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import com.tekihub.kameleon.watchtheme.receivers.ScreenStatusReceiver;
import javax.inject.Inject;

/**
 * Created by Jose on 23/4/16.
 */
public class KameleonService extends Service {
    @Inject ScreenStatusReceiver screenStatusReceiver;

    @Nullable @Override public IBinder onBind(Intent intent) {
        return null;
    }

    @Override public void onCreate() {
        super.onCreate();
        inject();

        registerReceiver(screenStatusReceiver, ScreenStatusReceiver.getIntentFilter());
    }

    @Override public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(screenStatusReceiver);
    }

    private void inject() {
        ((MainApplication) getApplication()).getWatchThemeComponent().inject(this);
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, KameleonService.class);
        context.startService(intent);
    }

    public static void stop(Context context) {
        Intent intent = new Intent(context, KameleonService.class);
        context.stopService(intent);
    }
}
