package com.tekihub.kameleon.main;

import android.os.Bundle;

import com.tekihub.kameleon.BaseActivity;
import com.tekihub.kameleon.R;
import com.tekihub.kameleon.utils.AppUsageUtils;
import com.tekihub.kameleon.watchtheme.AppThemeService;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (!AppUsageUtils.isAppUsageEnabled(getApplicationContext()) || !preferences.isEnabled()) {
            this.navigator.navigateToWelcome(this);
        }
        AppThemeService.start(this);
    }

    @OnClick(R.id.main_disable)
    protected void onClickDisable() {
        AppThemeService.stop(this);
        preferences.setEnabled(false);
        this.navigator.navigateToWelcome(this);
        finish();
    }

}
