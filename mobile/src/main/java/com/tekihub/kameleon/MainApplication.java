package com.tekihub.kameleon;

import android.app.Application;

import com.tekihub.kameleon.di.ApplicationComponent;
import com.tekihub.kameleon.di.ApplicationModule;
import com.tekihub.kameleon.di.DaggerApplicationComponent;
import com.tekihub.kameleon.watchtheme.communication.di.CommunicationModule;
import com.tekihub.kameleon.watchtheme.di.DaggerWatchThemeComponent;
import com.tekihub.kameleon.watchtheme.di.WatchThemeComponent;
import com.tekihub.kameleon.watchtheme.di.WatchThemeModule;
import com.tekihub.kameleon.watchtheme.theme.di.ThemeModule;
import com.tekihub.kameleon.watchtheme.usage.di.UsageModule;

public class MainApplication extends Application {

    private ApplicationComponent applicationComponent;
    private WatchThemeComponent watchThemeComponent;

    @Override public void onCreate() {
        super.onCreate();

        initComponent();
    }

    private void initComponent() {
        applicationComponent = DaggerApplicationComponent.builder()
                                                         .applicationModule(new ApplicationModule(
                                                                 getApplicationContext()))
                                                         .build();

        watchThemeComponent = DaggerWatchThemeComponent.builder()
                                                       .applicationComponent(applicationComponent)
                                                       .watchThemeModule(new WatchThemeModule())
                                                       .communicationModule(
                                                               new CommunicationModule())
                                                       .themeModule(new ThemeModule())
                                                       .usageModule(new UsageModule())
                                                       .build();

    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    public WatchThemeComponent getWatchThemeComponent() {
        return watchThemeComponent;
    }


}
