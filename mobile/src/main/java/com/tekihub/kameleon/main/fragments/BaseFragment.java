package com.tekihub.kameleon.main.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.tekihub.kameleon.AppPreferences;
import com.tekihub.kameleon.MainApplication;
import com.tekihub.kameleon.R;
import com.tekihub.kameleon.di.ApplicationComponent;
import com.tekihub.kameleon.main.Navigator;
import javax.inject.Inject;

public class BaseFragment extends Fragment {

    @Bind(R.id.welcome_body) protected TextView body;

    @Bind(R.id.welcome_button) protected Button button;

    @Inject protected Navigator navigator;

    @Inject protected AppPreferences preferences;

    public static Fragment newInstance(Context context, Class<? extends BaseFragment> cls) {
        return Fragment.instantiate(context, cls.getName());
    }

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApplicationComponent component = ((MainApplication) getActivity().getApplication()).getApplicationComponent();
        component.inject(this);
    }

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_base, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    protected void setBodyText(int textResId) {
        body.setText(textResId);
    }

    protected void setButtonText(int textResId) {
        button.setText(textResId);
    }

    @OnClick(R.id.welcome_button) protected void onButtonClick() {

    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
