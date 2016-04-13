package com.tekihub.kameleon.main.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tekihub.kameleon.R;

public class WelcomeFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        setBodyText(R.string.start_kameleon);
        setButtonText(R.string.lets_go);

        return view;
    }

    protected void onButtonClick() {
        preferences.setEnabled(true);
        navigator.navigateToMain(getActivity().getApplicationContext());
        if (isAdded() && getActivity() != null) {
            getActivity().finish();
        }
    }
}
