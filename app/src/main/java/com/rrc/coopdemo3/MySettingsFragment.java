package com.rrc.coopdemo3;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

public class MySettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

        if (true) {
            EditTextPreference signaturePreference = findPreference("signature");
            if (signaturePreference != null) {
                signaturePreference.setVisible(true);
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

    }
}
