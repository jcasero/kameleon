package com.tekihub.kameleon.watchtheme;

import com.tekihub.kameleon.watchtheme.communication.GoogleApiClientWrapper;
import com.tekihub.kameleon.watchtheme.di.WatchThemeScope;
import javax.inject.Inject;

@WatchThemeScope public class WatchThemeManagerImpl implements WatchThemeManager {
    private final GetApplicationTheme getApplicationTheme;
    private final GoogleApiClientWrapper googleApiClientWrapper;

    @Inject public WatchThemeManagerImpl(GetApplicationTheme getApplicationTheme,
        GoogleApiClientWrapper googleApiClientWrapper) {
        this.getApplicationTheme = getApplicationTheme;
        this.googleApiClientWrapper = googleApiClientWrapper;
    }

    @Override public void start() {
        googleApiClientWrapper.connect();
        getApplicationTheme.execute();
    }

    @Override public void stop() {
        googleApiClientWrapper.disconnect();
        getApplicationTheme.unsubscribe();
    }
}
