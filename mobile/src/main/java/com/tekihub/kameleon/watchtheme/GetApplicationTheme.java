package com.tekihub.kameleon.watchtheme;

import com.tekihub.kameleon.domain.ApplicationColorSet;
import com.tekihub.kameleon.executors.PostExecutionThread;
import com.tekihub.kameleon.executors.ThreadExecutor;
import com.tekihub.kameleon.watchtheme.di.WatchThemeScope;
import com.tekihub.kameleon.watchtheme.theme.GetApplicationPaletteTask;
import com.tekihub.kameleon.watchtheme.theme.functions.CheckColorSetNonNull;
import com.tekihub.kameleon.watchtheme.theme.functions.CheckEmptyString;
import com.tekihub.kameleon.watchtheme.usage.tasks.CheckForegroundAppTask;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import rx.Observable;
import rx.Subscription;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

@WatchThemeScope public class GetApplicationTheme {
    private Subscription subscription = Subscriptions.empty();

    private final GetApplicationThemeObserver subscriber;
    private final CheckForegroundAppTask checkForegroundAppTask;
    private final GetApplicationPaletteTask getApplicationPaletteTask;
    private final CheckEmptyString checkEmptyString;
    private final CheckColorSetNonNull checkColorSetNonNull;
    private final ThreadExecutor threadExecutor;
    private final PostExecutionThread postExecutionThread;

    @Inject public GetApplicationTheme(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread,
        CheckForegroundAppTask checkForegroundAppTask, GetApplicationPaletteTask getApplicationPaletteTask,
        GetApplicationThemeObserver subscriber, CheckEmptyString checkEmptyString,
        CheckColorSetNonNull checkColorSetNonNull) {
        this.subscriber = subscriber;
        this.checkForegroundAppTask = checkForegroundAppTask;
        this.getApplicationPaletteTask = getApplicationPaletteTask;
        this.checkEmptyString = checkEmptyString;
        this.checkColorSetNonNull = checkColorSetNonNull;
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
    }

    @SuppressWarnings("unchecked") public void execute() {
        this.subscription = buildObservable().subscribeOn(Schedulers.from(threadExecutor))
            .observeOn(postExecutionThread.getScheduler())
            .subscribe(subscriber);
    }

    private Observable<ApplicationColorSet> buildObservable() {
        return Observable.interval(1, TimeUnit.SECONDS)
            .map(checkForegroundAppTask)
            .filter(checkEmptyString)
            .map(getApplicationPaletteTask)
            .filter(checkColorSetNonNull);
    }

    public void unsubscribe() {
        if (!subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}
