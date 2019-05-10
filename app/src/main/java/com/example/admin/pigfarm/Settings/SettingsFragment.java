package com.example.admin.pigfarm.Settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.example.admin.R;

public class SettingsFragment extends PreferenceFragment{

    SharedPreferences sp;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PreferenceManager.setDefaultValues(getActivity(), R.xml.pref_settings, false);
        addPreferencesFromResource(R.xml.pref_settings);

        onPreferenceValueChanged();

    }

    private void onPreferenceValueChanged(){
        final SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this.getActivity());

        pref.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
                String unit = sharedPreferences.getString("unit_settings","");

                Preference preference = findPreference("unit_settings");
                preference.setSummary(unit);
            }
        });
    }



}
