package com.tekihub.kameleon.watchtheme.theme.functions;

import com.tekihub.kameleon.domain.ApplicationColorSet;
import com.tekihub.kameleon.watchtheme.di.WatchThemeScope;
import javax.inject.Inject;
import rx.functions.Func1;

@WatchThemeScope public class CheckColorSetNonNull implements Func1<ApplicationColorSet, Boolean> {

    @Inject public CheckColorSetNonNull() {

    }

    @Override public Boolean call(ApplicationColorSet applicationColorSet) {
        return applicationColorSet != null;
    }
}
