package com.github.ncliff.passet.presentation.ui.settings

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.github.ncliff.passet.R
import com.github.ncliff.passet.presentation.models.SharedDatabaseViewModel

class SettingsFragment : PreferenceFragmentCompat() {
    private val viewModel: SharedDatabaseViewModel by lazy {
        ViewModelProvider(requireActivity()).get(SharedDatabaseViewModel::class.java)
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }

    override fun onPreferenceTreeClick(preference: Preference): Boolean {
        val key = preference.key
        if (key == "delete_all") {
            showAlertDeleteDialog()
            return true
        }
        return false
    }

    private fun showAlertDeleteDialog() {
        val builder = AlertDialog.Builder(requireActivity())

        builder.setTitle(R.string.delete_all_note)
            .setIcon(R.drawable.ic_baseline_warning_24)
            .setMessage(R.string.question_for_deletion)
            .setNegativeButton(R.string.no) { dialog, _ ->
                dialog.cancel()
            }
            .setPositiveButton(R.string.yes) { dialog, _ ->
                viewModel.deleteAll {}
                dialog.cancel()
            }
            .show()
    }
}