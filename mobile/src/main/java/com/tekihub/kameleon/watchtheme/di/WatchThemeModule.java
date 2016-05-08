package com.tekihub.kameleon.watchtheme.di;

import com.tekihub.kameleon.executors.PostExecutionThread;
import com.tekihub.kameleon.executors.ThreadExecutor;
import com.tekihub.kameleon.watchtheme.GetApplicationTheme;
import com.tekihub.kameleon.watchtheme.GetApplicationThemeObserver;
import com.tekihub.kameleon.watchtheme.WatchThemeManager;
import com.tekihub.kameleon.watchtheme.WatchThemeManagerImpl;
import com.tekihub.kameleon.watchtheme.communication.GoogleApiClientWrapper;
import com.tekihub.kameleon.watchtheme.receivers.ScreenStatusReceiver;
import com.tekihub.kameleon.watchtheme.theme.GetApplicationPaletteTask;
import com.tekihub.kameleon.watchtheme.theme.functions.CheckColorSetNonNull;
import com.tekihub.kameleon.watchtheme.theme.functions.CheckEmptyString;
import com.tekihub.kameleon.watchtheme.usage.tasks.CheckForegroundAppTask;
import dagger.Module;
import dagger.Provides;

@Module public class WatchThemeModule {

    @WatchThemeScope @Provides GetApplicationTheme providesGetApplicationTheme(ThreadExecutor threadExecutor,
        PostExecutionThread postExecutionThread, CheckForegroundAppTask checkForegroundAppTask,
        GetApplicationPaletteTask getApplicationPaletteTask, GetApplicationThemeObserver subscriber,
        CheckEmptyString checkEmptyString, CheckColorSetNonNull checkColorSetNonNull) {

        return new GetApplicationTheme(threadExecutor, postExecutionThread, checkForegroundAppTask,
            getApplicationPaletteTask, subscriber, checkEmptyString, checkColorSetNonNull);
    }

    @Provides @WatchThemeScope WatchThemeManager provideWatchThemeManager(GetApplicationTheme getApplicationTheme,
        GoogleApiClientWrapper googleApiClientWrapper) {
        return new WatchThemeManagerImpl(getApplicationTheme, googleApiClientWrapper);
    }

    @Provides @WatchThemeScope ScreenStatusReceiver provideScreenStatusReceiver(WatchThemeManager watchThemeManager) {
        return new ScreenStatusReceiver(watchThemeManager);
    }
}
