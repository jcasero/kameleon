package com.tekihub.kameleon.watchtheme;

import com.tekihub.kameleon.domain.ApplicationColorSet;
import com.tekihub.kameleon.watchtheme.communication.DataSender;
import com.tekihub.kameleon.watchtheme.di.WatchThemeScope;

import javax.inject.Inject;

import rx.Subscriber;

@WatchThemeScope
public class GetApplicationThemeSubscriber extends Subscriber<ApplicationColorSet> {
    //TODO: Inject a color sender to the smartwatch
    private static final String TAG = "GetApplicationThemeSubscriber";
    private DataSender dataSender;

    @Inject
    public GetApplicationThemeSubscriber(DataSender dataSender) {
        this.dataSender = dataSender;
    }

    @Override public void onCompleted() {

    }

    @Override public void onError(Throwable e) {

    }

    @Override public void onNext(ApplicationColorSet applicationColorSet) {
        dataSender.send(applicationColorSet);
    }
}
