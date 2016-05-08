package com.tekihub.kameleon.executors;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Scheduler;
import rx.schedulers.Schedulers;

@Singleton
public class PostExecutor implements PostExecutionThread {

    @Inject
    public PostExecutor() {

    }

    @Override public Scheduler getScheduler() {
        return Schedulers.io();
    }
}
