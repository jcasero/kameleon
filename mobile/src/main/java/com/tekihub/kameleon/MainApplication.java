package com.tekihub.kameleon;

import android.app.Application;

import com.tekihub.kameleon.di.ApplicationComponent;
import com.tekihub.kameleon.di.ApplicationModule;
import com.tekihub.kameleon.di.DaggerApplicationComponent;

public class MainApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        initComponent();
    }

    private void initComponent() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(getApplicationContext()))
                .build();

    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
