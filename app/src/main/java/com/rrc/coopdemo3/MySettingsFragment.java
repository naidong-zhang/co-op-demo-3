package com.rrc.coopdemo3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

public class MySettingsFragment extends PreferenceFragmentCompat {

    private SharedPreferences.OnSharedPreferenceChangeListener listener;
    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

        if (true) {
            EditTextPreference signaturePreference = findPreference("signature");
            if (signaturePreference != null) {
                signaturePreference.setVisible(true);

                signaturePreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                    @Override
                    public boolean onPreferenceChange(Preference preference, Object newValue) {
                        Toast.makeText(getActivity(), "new signature is: " + newValue.toString(), Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
            }
        }

        Preference webpage2Preference = findPreference("webpage2");
        if (webpage2Preference != null) {
            webpage2Preference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("https://www.google.com/search?q=winnipeg"));
                    webpage2Preference.setIntent(intent);

                    return false;
                }
            });
        }

        listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                String toast = "";
                if (key.equals("notifications")) {
                    boolean are_notifications_enabled = sharedPreferences.getBoolean(key, false);
                    toast = "notifications changed to: " + are_notifications_enabled;
                } else if (key.equals("list")) {
                    String v = sharedPreferences.getString(key, "not set");
                    toast = "List Preference changed to: " + v;
                } else if (key.equals("seekbar")) {
                    int v = sharedPreferences.getInt(key, 0);
                    toast = "Volume set to: " + v;
                }
                Toast.makeText(getActivity(), toast, Toast.LENGTH_SHORT).show();
            }
        };


        ListPreference listPreference = findPreference("list");
        if (listPreference != null) {
            CharSequence[] entries = {"Item 1", "Item 2"};
            listPreference.setEntries(entries);
            CharSequence[] values = {"1", "2"};
            listPreference.setEntryValues(values);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(listener);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(listener);
    }
}
