package com.adhibuchori.finalsubmissionbfaa.ui.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.adhibuchori.finalsubmissionbfaa.databinding.ActivitySettingsBinding
import com.adhibuchori.finalsubmissionbfaa.ui.viewmodel.HomeViewModel
import com.adhibuchori.finalsubmissionbfaa.ui.viewmodel.ViewModelFactory

class SettingsActivity : AppCompatActivity() {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    private val viewModel: HomeViewModel by viewModels {
        ViewModelFactory.getInstanceWithDataStore(application, dataStore)
    }

    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val setActionBar = supportActionBar
        setActionBar?.title = "Setting"
        setActionBar?.setDisplayHomeAsUpEnabled(true)

        with(binding) {
            viewModel.getThemeSetting()
                ?.observe(this@SettingsActivity) { isDarkModeActive: Boolean ->
                    if (isDarkModeActive) {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                        switchTheme.isChecked = true
                    } else {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                        switchTheme.isChecked = false
                    }
                }
            switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
                viewModel.saveThemeSetting(isChecked)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        this.onBackPressedDispatcher.onBackPressed()
        return true
    }
}