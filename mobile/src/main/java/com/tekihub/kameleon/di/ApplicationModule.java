package com.tekihub.kameleon.di;

import android.content.Context;

import com.tekihub.kameleon.AppPreferences;
import com.tekihub.kameleon.executors.JobExecutor;
import com.tekihub.kameleon.executors.PostExecutionThread;
import com.tekihub.kameleon.executors.PostExecutor;
import com.tekihub.kameleon.executors.ThreadExecutor;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public class ApplicationModule {
    public static final String NAMED_APP_CONTEXT = "AppContext";
    private Context context;

    public ApplicationModule(Context context) {
        this.context = context;
    }

    @Provides @Singleton @Named(NAMED_APP_CONTEXT) Context providesApplicationContext() {
        return this.context;
    }

    @Provides @Singleton AppPreferences provideAppPreferences(
            @Named(ApplicationModule.NAMED_APP_CONTEXT) Context context) {
        return new AppPreferences(context);
    }

    @Provides @Singleton ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
        return jobExecutor;
    }

    @Provides @Singleton PostExecutionThread providePostExecutionThread(PostExecutor postExecutor) {
        return postExecutor;
    }
}
