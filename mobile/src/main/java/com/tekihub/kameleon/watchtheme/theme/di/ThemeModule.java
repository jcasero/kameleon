package com.tekihub.kameleon.watchtheme.theme.di;

import android.content.Context;

import com.tekihub.kameleon.di.ApplicationModule;
import com.tekihub.kameleon.watchtheme.di.WatchThemeScope;
import com.tekihub.kameleon.watchtheme.theme.GetApplicationPaletteTask;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class ThemeModule {

    public ThemeModule() {

    }

    @WatchThemeScope
    @Provides
    GetApplicationPaletteTask providesGetApplicationPaletteTask(@Named(ApplicationModule.NAMED_APP_CONTEXT) Context context) {
        return new GetApplicationPaletteTask(context);
    }
}
