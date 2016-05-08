package com.tekihub.kameleon.main;

import android.os.Bundle;
import com.tekihub.kameleon.BaseActivity;
import com.tekihub.kameleon.R;
import com.tekihub.kameleon.main.fragments.AppUsageFragment;
import com.tekihub.kameleon.main.fragments.BaseFragment;
import com.tekihub.kameleon.main.fragments.WelcomeFragment;
import com.tekihub.kameleon.utils.AppUsageUtils;

public class WelcomeActivity extends BaseActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    @Override protected void onResume() {
        super.onResume();

        // TODO: Remove existing fragment before add a new one
        addFragment(R.id.fragment_container,
            AppUsageUtils.isAppUsageEnabled(getApplicationContext()) ? BaseFragment.newInstance(this,
                WelcomeFragment.class) : BaseFragment.newInstance(this, AppUsageFragment.class));
    }
}
