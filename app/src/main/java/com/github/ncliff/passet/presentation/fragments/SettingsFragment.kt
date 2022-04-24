package com.github.ncliff.passet.presentation.fragments

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.github.ncliff.passet.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}