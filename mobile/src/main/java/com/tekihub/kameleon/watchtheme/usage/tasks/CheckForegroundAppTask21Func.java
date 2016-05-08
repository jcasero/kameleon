package com.tekihub.kameleon.watchtheme.usage.tasks;

import android.annotation.TargetApi;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.tekihub.kameleon.watchtheme.di.WatchThemeScope;

import javax.inject.Inject;

@WatchThemeScope
public class CheckForegroundAppTask21Func extends CheckForegroundAppTask {
    private static final String USAGE_STATS_SERVICE = "usagestats";

    @Inject public CheckForegroundAppTask21Func(Context context) {
        super(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP) @Override public String call(Long interval) {
        final UsageStatsManager usageStatsManager = (UsageStatsManager) context.getSystemService(USAGE_STATS_SERVICE);
        long current = System.currentTimeMillis();
        UsageEvents events = usageStatsManager.queryEvents(current - 2000, current);
        UsageEvents.Event event = new UsageEvents.Event();
        String packageName = "";
        while (events.hasNextEvent()) {
            events.getNextEvent(event);
            if (event.getEventType() == UsageEvents.Event.MOVE_TO_FOREGROUND) {
                packageName = event.getPackageName();
            }
        }

        return packageName;
    }
}
