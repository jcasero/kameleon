package com.tekihub.kameleon.watchtheme.communication.di;

import android.content.Context;
import com.tekihub.kameleon.di.ApplicationModule;
import com.tekihub.kameleon.watchtheme.communication.DataSender;
import com.tekihub.kameleon.watchtheme.communication.GoogleApiClientWrapper;
import com.tekihub.kameleon.watchtheme.di.WatchThemeScope;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;

@Module public class CommunicationModule {

    @WatchThemeScope @Provides GoogleApiClientWrapper provideWearGoogleApiClient(
        @Named(ApplicationModule.NAMED_APP_CONTEXT) Context context) {
        return new GoogleApiClientWrapper(context);
    }

    @WatchThemeScope @Provides DataSender provideDataSender(GoogleApiClientWrapper googleApiClientWrapper) {
        return new DataSender(googleApiClientWrapper);
    }
}
