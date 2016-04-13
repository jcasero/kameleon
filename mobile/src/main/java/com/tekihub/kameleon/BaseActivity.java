package com.tekihub.kameleon;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.tekihub.kameleon.di.ApplicationComponent;
import com.tekihub.kameleon.main.Navigator;

import javax.inject.Inject;

public class BaseActivity extends AppCompatActivity {
    @Inject protected Navigator navigator;

    @Inject protected AppPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApplicationComponent component = ((MainApplication) getApplication()).getApplicationComponent();
        component.inject(this);
    }

    protected void addFragment(int containerId, Fragment fragment) {
        getFragmentManager()
                .beginTransaction()
                .add(containerId, fragment)
                .commit();
    }

}
