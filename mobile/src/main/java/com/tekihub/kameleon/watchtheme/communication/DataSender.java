package com.tekihub.kameleon.watchtheme.communication;

import com.tekihub.kameleon.domain.ApplicationColorSet;
import com.tekihub.kameleon.watchtheme.di.WatchThemeScope;

import javax.inject.Inject;

@WatchThemeScope
public class DataSender {
    private static final String TAG = "DataSender";
    private final GoogleApiClientWrapper googleApiClientWrapper;

    @Inject
    public DataSender(GoogleApiClientWrapper googleApiClientWrapper) {
        this.googleApiClientWrapper = googleApiClientWrapper;
    }

    public void send(ApplicationColorSet applicationColorSet) {
        googleApiClientWrapper.send(applicationColorSet);
    }


}
