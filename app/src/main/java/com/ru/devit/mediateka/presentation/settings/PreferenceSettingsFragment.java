package com.ru.devit.mediateka.presentation.settings;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

import com.ru.devit.mediateka.R;

public class PreferenceSettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_mediateka_settings);

        bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_key_background_image_quality_type)));
    }

    private void bindPreferenceSummaryToValue(Preference preference) {
        preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);

        sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), ""));
    }

    private final Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = (preference, newValue) -> {
        String stringValue = newValue.toString();

        if (preference instanceof ListPreference) {

            ListPreference listPreference = (ListPreference) preference;
            int index = listPreference.findIndexOfValue(stringValue);

            preference.setSummary(index >= 0 ?
                    qualitySummary(listPreference.getEntries()[index].toString())
                    : qualitySummary(listPreference.getEntries()[0].toString()));
        }
        return true;
    };

    private String qualitySummary(String quality){
        return String.format("%s: %s" , getString(R.string.background_poster_image_quality) , quality);
    }
}
