package com.tekihub.kameleon.watchtheme.communication.di;

import android.content.Context;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Wearable;
import com.tekihub.kameleon.di.ApplicationModule;
import com.tekihub.kameleon.watchtheme.communication.DataSender;
import com.tekihub.kameleon.watchtheme.di.WatchThemeScope;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class CommunicationModule {

    GoogleApiClient.ConnectionCallbacks        connectionCallbacks;
    GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener;

    public CommunicationModule(GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        this.connectionCallbacks = connectionCallbacks;
        this.onConnectionFailedListener = onConnectionFailedListener;
    }

    @WatchThemeScope
    @Provides GoogleApiClient provideWearGoogleApiClient(@Named(ApplicationModule.NAMED_APP_CONTEXT) Context context) {
        return new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(connectionCallbacks)
                .addOnConnectionFailedListener(onConnectionFailedListener)
                .addApi(Wearable.API)
                .build();
    }

    @WatchThemeScope
    @Provides DataSender provideDataSender(GoogleApiClient googleApiClient) {
        return new DataSender(googleApiClient);
    }
}
