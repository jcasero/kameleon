package com.tekihub.kameleon.watchtheme.usage.tasks;

import android.content.Context;
import android.os.Build;

import com.tekihub.kameleon.watchtheme.di.WatchThemeScope;

import javax.inject.Inject;

@WatchThemeScope
public class CheckForegroundAppFactory {

    private CheckForegroundAppTask runner;

    @Inject
    public CheckForegroundAppFactory(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            runner = new CheckForegroundAppTask21Func(context);
        } else {
            runner = new CheckForegroundAppTask20Func(context);
        }
    }

    public CheckForegroundAppTask provide() {
        return runner;
    }
}
