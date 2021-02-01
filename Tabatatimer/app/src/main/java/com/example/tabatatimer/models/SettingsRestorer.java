package com.example.tabatatimer.models;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;

import androidx.appcompat.app.AppCompatDelegate;

import java.util.Locale;

import static com.example.tabatatimer.models.constants.switchFontSizeKey;
import static com.example.tabatatimer.models.constants.switchLanguageKey;
import static com.example.tabatatimer.models.constants.switchThemeKey;

public class SettingsRestorer {
    public static void restoreSettings(SharedPreferences preferences, Context context) {
        Configuration configuration = new Configuration();
        configuration.locale = getSelectedLocale(preferences.getString(switchLanguageKey, "en"));
        configuration.fontScale = Float.parseFloat(preferences.getString(switchFontSizeKey, "1.25"));

        context.getResources().updateConfiguration(configuration, null);
        setTheme(preferences);
    }

    public static void restoreFontSize(SharedPreferences preferences, Context context) {
        Configuration configuration = new Configuration();
        configuration.fontScale = Float.parseFloat(preferences.getString(switchFontSizeKey, "1.25"));
        context.getResources().updateConfiguration(configuration, null);
    }

    public static void restoreLanguage(SharedPreferences preferences, Context context) {
        Configuration configuration = new Configuration();
        configuration.locale = getSelectedLocale(preferences.getString(switchLanguageKey, "en"));
        context.getResources().updateConfiguration(configuration, null);
    }

    public static void setTheme(SharedPreferences preferences) {
        final boolean isNighThemeSelected = preferences.getBoolean(switchThemeKey, false);
        if (isNighThemeSelected) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    private static Locale getSelectedLocale(final String selectedLanguage) {
        Locale locale = new Locale(selectedLanguage);
        Locale.setDefault(locale);
        return locale;
    }


}
