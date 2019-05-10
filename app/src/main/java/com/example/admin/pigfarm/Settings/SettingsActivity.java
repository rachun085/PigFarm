package com.example.admin.pigfarm.Settings;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;


import com.example.admin.R;


public class SettingsActivity extends PreferenceActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PreferenceManager.setDefaultValues(this, R.xml.pref_settings, false);
        setContentView(R.layout.settingsframelayout);

        FragmentManager manager = getFragmentManager();
        FragmentTransaction fragtrans = manager.beginTransaction();
        SettingsFragment settings = new SettingsFragment();
        fragtrans.replace(R.id.fragment_settings, settings);
        fragtrans.commit();


    }

}
