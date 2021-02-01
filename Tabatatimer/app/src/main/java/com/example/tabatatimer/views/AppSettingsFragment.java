package com.example.tabatatimer.views;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import com.example.tabatatimer.R;
import com.example.tabatatimer.models.SettingsRestorer;
import com.example.tabatatimer.viewModels.WorkoutViewModel;

import static com.example.tabatatimer.models.constants.switchFontSizeKey;
import static com.example.tabatatimer.models.constants.switchLanguageKey;
import static com.example.tabatatimer.models.constants.switchThemeKey;

public class AppSettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {
    SharedPreferences preferences;
    private WorkoutViewModel workoutVM;

    public AppSettingsFragment() {
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        workoutVM = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())).
                get(WorkoutViewModel.class);

        setPreferencesFromResource(R.xml.app_settings, null);
        preferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
        preferences.registerOnSharedPreferenceChangeListener(this);

        findPreference("clear_all").setOnPreferenceClickListener(preference -> {
            workoutVM.deleteAllWorkouts();
            return true;
        });
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        switch (key) {
            case switchThemeKey:
                SettingsRestorer.setTheme(sharedPreferences);
                break;
            case switchFontSizeKey:
                SettingsRestorer.restoreFontSize(preferences, requireContext());
                requireActivity().recreate();
                break;
            case switchLanguageKey:
                SettingsRestorer.restoreLanguage(preferences, requireContext());
                requireActivity().recreate();
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        preferences.unregisterOnSharedPreferenceChangeListener(this);
    }
}
