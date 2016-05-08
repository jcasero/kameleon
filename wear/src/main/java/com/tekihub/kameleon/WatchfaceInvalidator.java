package com.tekihub.kameleon;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created by Jose on 4/5/16.
 */
public class WatchfaceInvalidator {
    private OnInvalidateListener invalidateListener;
    private ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    private ScheduledFuture<?> scheduledFuture;
    private Runnable runnable = new Runnable() {
        @Override public void run() {
            invalidateListener.invalidate();
        }
    };

    public WatchfaceInvalidator(OnInvalidateListener listener) {
        if (listener == null) {
            throw new IllegalStateException("WatchfaceOperations cannot be null");
        }

        invalidateListener = listener;
    }

    public void start() {
        scheduledFuture = executor.scheduleAtFixedRate(runnable, 0L, 1L, TimeUnit.MILLISECONDS);
    }

    public void stop() {
        if (scheduledFuture != null) {
            scheduledFuture.cancel(true);
        }
    }

    public interface OnInvalidateListener {
        void invalidate();
    }
}
