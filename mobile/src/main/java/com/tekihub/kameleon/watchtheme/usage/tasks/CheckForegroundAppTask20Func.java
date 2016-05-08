package com.tekihub.kameleon.watchtheme.usage.tasks;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import com.tekihub.kameleon.watchtheme.di.WatchThemeScope;
import java.util.List;
import javax.inject.Inject;

@WatchThemeScope public class CheckForegroundAppTask20Func extends CheckForegroundAppTask {

    @Inject public CheckForegroundAppTask20Func(Context context) {
        super(context);
    }

    @Override public String call(Long interval) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = "";
        List<ActivityManager.RunningTaskInfo> runningTasks = activityManager.getRunningTasks(1);
        if (runningTasks.size() > 0) {
            ComponentName componentName = runningTasks.get(0).topActivity;
            if (componentName != null) {
                packageName = componentName.getPackageName();
            }
        }
        return packageName;
    }
}
