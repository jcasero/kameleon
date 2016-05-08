package com.tekihub.kameleon.di;

import android.content.Context;

import com.tekihub.kameleon.AppPreferences;
import com.tekihub.kameleon.BaseActivity;
import com.tekihub.kameleon.executors.PostExecutionThread;
import com.tekihub.kameleon.executors.ThreadExecutor;
import com.tekihub.kameleon.main.Navigator;
import com.tekihub.kameleon.main.fragments.BaseFragment;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(BaseActivity activity);

    void inject(BaseFragment fragment);

    @Named(ApplicationModule.NAMED_APP_CONTEXT) Context context();
    ThreadExecutor threadExecutor();
    PostExecutionThread postExecutionThread();
    AppPreferences appPreferences();
    Navigator navigator();
}
