package com.example.tabatatimer.views;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import com.example.tabatatimer.R;
import com.example.tabatatimer.models.SettingsRestorer;

public class AppSettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_TabataTimer);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_settings);
        SettingsRestorer.restoreSettings(PreferenceManager.getDefaultSharedPreferences(this), this);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings_layout, new AppSettingsFragment())
                .commit();
    }
}