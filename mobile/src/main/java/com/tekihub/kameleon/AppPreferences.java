package com.tekihub.kameleon;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AppPreferences {
    private static final String KAMELEON_PREFS = "kameleon_prefs";

    private static final String KEY_ENABLED = "enabled";
    private SharedPreferences preferences;

    @Inject
    public AppPreferences(Context context) {
        preferences = context.getSharedPreferences(KAMELEON_PREFS, Context.MODE_PRIVATE);
    }

    public void setEnabled(boolean state) {
        preferences.edit().putBoolean(KEY_ENABLED, state).apply();
    }

    public boolean isEnabled() {
        return preferences.getBoolean(KEY_ENABLED, false);
    }
}
