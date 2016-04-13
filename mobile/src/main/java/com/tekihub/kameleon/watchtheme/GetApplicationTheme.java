package com.tekihub.kameleon.watchtheme;

import com.tekihub.kameleon.domain.ApplicationColorSet;
import com.tekihub.kameleon.executors.PostExecutionThread;
import com.tekihub.kameleon.executors.ThreadExecutor;
import com.tekihub.kameleon.watchtheme.di.WatchThemeScope;
import com.tekihub.kameleon.watchtheme.theme.GetApplicationPaletteTask;
import com.tekihub.kameleon.watchtheme.usage.tasks.CheckForegroundAppTask;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

@WatchThemeScope
public class GetApplicationTheme {
    private static final String TAG                  = "GetApplicationTheme";
    private static final long   EXECUTOR_REPEAT_TIME = 1000L;

    private final ThreadExecutor      threadExecutor;
    private final PostExecutionThread postExecutionThread;

    private Subscription subscription = Subscriptions.empty();

    private GetApplicationThemeSubscriber subscriber;
    private CheckForegroundAppTask        checkForegroundAppTask;
    private GetApplicationPaletteTask     getApplicationPaletteTask;

    @Inject
    public GetApplicationTheme(ThreadExecutor threadExecutor,
                               PostExecutionThread postExecutionThread,
                               CheckForegroundAppTask checkForegroundAppTask,
                               GetApplicationPaletteTask getApplicationPaletteTask,
                               GetApplicationThemeSubscriber subscriber) {
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
        this.subscriber = subscriber;
        this.checkForegroundAppTask = checkForegroundAppTask;
        this.getApplicationPaletteTask = getApplicationPaletteTask;
    }

    @SuppressWarnings("unchecked")
    public void execute() {
        this.subscription = buildObservable()
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.getScheduler())
                .subscribe(subscriber);
    }

    private Observable buildObservable() {
        return Observable.create(new Observable.OnSubscribe<ApplicationColorSet>() {
            @Override public void call(final Subscriber<? super ApplicationColorSet> subscriber) {
                Observable.interval(EXECUTOR_REPEAT_TIME, TimeUnit.MILLISECONDS)
                        .map(checkForegroundAppTask)
                        .filter(new Func1<String, Boolean>() {
                            @Override public Boolean call(String s) {
                                return s != null && !s.isEmpty();
                            }
                        })
                        .map(getApplicationPaletteTask)
                        .filter(new Func1<ApplicationColorSet, Boolean>() {
                            @Override public Boolean call(ApplicationColorSet applicationColorSet) {
                                return applicationColorSet != null;
                            }
                        })
                        .retry()
                        .map(new Func1<ApplicationColorSet, Long>() {
                            @Override public Long call(ApplicationColorSet applicationColorSet) {
                                subscriber.onNext(applicationColorSet);
                                return 1L;
                            }
                        })
                        .subscribe(new Action1<Long>() {
                            @Override public void call(Long aLong) {

                            }
                        });
            }
        });
    }

    public void unsubscribe() {
        if (!subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}
