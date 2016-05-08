package com.tekihub.kameleon.watchtheme;

import com.tekihub.kameleon.domain.ApplicationColorSet;
import com.tekihub.kameleon.watchtheme.communication.DataSender;
import com.tekihub.kameleon.watchtheme.di.WatchThemeScope;
import javax.inject.Inject;
import rx.Observer;

@WatchThemeScope public class GetApplicationThemeObserver implements Observer<ApplicationColorSet> {
    private DataSender dataSender;

    @Inject public GetApplicationThemeObserver(DataSender dataSender) {
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
