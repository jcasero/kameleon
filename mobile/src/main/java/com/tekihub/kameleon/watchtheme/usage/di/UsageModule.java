package com.tekihub.kameleon.watchtheme.usage.di;

import android.content.Context;

import com.tekihub.kameleon.di.ApplicationModule;
import com.tekihub.kameleon.watchtheme.usage.tasks.CheckForegroundAppFactory;
import com.tekihub.kameleon.watchtheme.usage.tasks.CheckForegroundAppTask;
import com.tekihub.kameleon.watchtheme.di.WatchThemeScope;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class UsageModule {

    @WatchThemeScope
    @Provides
    CheckForegroundAppTask providesCheckForegroundAppTask(@Named(ApplicationModule.NAMED_APP_CONTEXT) Context context) {
        return new CheckForegroundAppFactory(context).provide();
    }
}
