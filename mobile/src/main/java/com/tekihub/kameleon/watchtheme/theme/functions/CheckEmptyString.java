package com.tekihub.kameleon.watchtheme.theme.functions;

import com.tekihub.kameleon.watchtheme.di.WatchThemeScope;
import javax.inject.Inject;
import rx.functions.Func1;

/**
 * Created by Jose on 24/4/16.
 */
@WatchThemeScope public class CheckEmptyString implements Func1<String, Boolean> {

    @Inject public CheckEmptyString() {

    }

    @Override public Boolean call(String s) {
        return s != null && !s.isEmpty();
    }
}
