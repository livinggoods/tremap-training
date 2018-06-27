package com.expansion.lg.kimaru.training.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.Preference;

import com.expansion.lg.kimaru.training.R;

public class SetttingFragment extends PreferenceFragmentCompat {
    SharedPreferences sharedPreferences;

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        //add xml
        addPreferencesFromResource(R.xml.preferences);


        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
    }
}