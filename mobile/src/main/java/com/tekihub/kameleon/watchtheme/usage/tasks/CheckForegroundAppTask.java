package com.tekihub.kameleon.watchtheme.usage.tasks;

import android.content.Context;
import rx.functions.Func1;

public abstract class CheckForegroundAppTask implements Func1<Long, String> {
    protected Context context;

    public CheckForegroundAppTask(Context context) {
        this.context = context;
    }
}
