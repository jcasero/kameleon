package com.tekihub.kameleon.watchtheme;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.tekihub.kameleon.MainApplication;
import com.tekihub.kameleon.watchtheme.communication.di.CommunicationModule;
import com.tekihub.kameleon.watchtheme.di.DaggerWatchThemeComponent;
import com.tekihub.kameleon.watchtheme.di.WatchThemeComponent;

import javax.inject.Inject;

public class AppThemeService extends Service implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    @Inject GoogleApiClient     googleApiClient;
    @Inject GetApplicationTheme getApplicationTheme;

    public static void start(Context context) {
        context.startService(new Intent(context, AppThemeService.class));
    }

    public static void stop(Context context) {
        context.stopService(new Intent(context, AppThemeService.class));
    }

    @Override public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        inject();

        // Connect to the wear device when the service is created
        googleApiClient.connect();
    }

    private void inject() {
        WatchThemeComponent component = DaggerWatchThemeComponent.builder()
                .applicationComponent(((MainApplication) getApplication()).getApplicationComponent())
                .communicationModule(new CommunicationModule(this, this))
                .build();
        component.inject(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        googleApiClient.disconnect();
        super.onDestroy();
    }

    @Override public void onConnected(@Nullable Bundle bundle) {
        getApplicationTheme.execute();
    }

    @Override public void onConnectionSuspended(int i) {
        getApplicationTheme.unsubscribe();
    }

    @Override public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        getApplicationTheme.unsubscribe();
    }
}
