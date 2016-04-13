package com.tekihub.kameleon.main.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tekihub.kameleon.R;

public class AppUsageFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        setBodyText(R.string.enable_app_usage);
        setButtonText(R.string.enable);

        return view;
    }

    protected void onButtonClick() {
        this.navigator.navigateToAppUsageAccessSettings(getActivity().getApplicationContext());
    }
}
