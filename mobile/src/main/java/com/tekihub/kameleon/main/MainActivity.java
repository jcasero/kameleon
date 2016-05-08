package com.tekihub.kameleon.main;

import android.os.Bundle;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.tekihub.kameleon.BaseActivity;
import com.tekihub.kameleon.KameleonService;
import com.tekihub.kameleon.R;
import com.tekihub.kameleon.utils.AppUsageUtils;

public class MainActivity extends BaseActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (!AppUsageUtils.isAppUsageEnabled(getApplicationContext()) || !preferences.isEnabled()) {
            this.navigator.navigateToWelcome(this);
        }
    }

    @OnClick(R.id.main_disable) protected void onClickDisable() {
        KameleonService.stop(this.getApplicationContext());
        preferences.setEnabled(false);
        this.navigator.navigateToWelcome(this);
        finish();
    }
}
