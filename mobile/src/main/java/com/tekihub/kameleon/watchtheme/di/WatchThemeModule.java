package com.tekihub.kameleon.watchtheme.di;

import com.tekihub.kameleon.watchtheme.GetApplicationTheme;
import com.tekihub.kameleon.watchtheme.GetApplicationThemeSubscriber;
import com.tekihub.kameleon.watchtheme.usage.tasks.CheckForegroundAppTask;
import com.tekihub.kameleon.executors.PostExecutionThread;
import com.tekihub.kameleon.executors.ThreadExecutor;
import com.tekihub.kameleon.watchtheme.theme.GetApplicationPaletteTask;

import dagger.Module;
import dagger.Provides;

@Module
public class WatchThemeModule {

    @WatchThemeScope
    @Provides GetApplicationTheme providesGetApplicationTheme(ThreadExecutor threadExecutor,
                                                              PostExecutionThread postExecutionThread,
                                                              CheckForegroundAppTask checkForegroundAppTask,
                                                              GetApplicationPaletteTask getApplicationPaletteTask,
                                                              GetApplicationThemeSubscriber subscriber) {

        return new GetApplicationTheme(threadExecutor, postExecutionThread, checkForegroundAppTask, getApplicationPaletteTask, subscriber);
    }
}
