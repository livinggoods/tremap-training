package com.expansion.lg.kimaru.training.fragments


import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.preference.PreferenceFragmentCompat
import android.support.v7.preference.Preference

import com.expansion.lg.kimaru.training.R

class SetttingFragment : PreferenceFragmentCompat() {
    internal var sharedPreferences: SharedPreferences

    override fun onCreatePreferences(bundle: Bundle, s: String) {
        //add xml
        addPreferencesFromResource(R.xml.preferences)


        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity)
    }
}