package com.adhibuchori.finalsubmissionbfaa.ui.activity

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.adhibuchori.finalsubmissionbfaa.R
import com.adhibuchori.finalsubmissionbfaa.databinding.ActivityHomeBinding
import com.adhibuchori.finalsubmissionbfaa.ui.adapter.HomeSectionsPagerAdapter
import com.adhibuchori.finalsubmissionbfaa.ui.viewmodel.HomeViewModel
import com.adhibuchori.finalsubmissionbfaa.ui.viewmodel.ViewModelFactory
import com.google.android.material.tabs.TabLayoutMediator

class HomeActivity : AppCompatActivity() {

    private var _binding: ActivityHomeBinding? = null
    private val binding get() = _binding!!

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    private val viewModel: HomeViewModel by viewModels {
        ViewModelFactory.getInstanceWithDataStore(application, dataStore)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val homeSectionsPagerAdapter = HomeSectionsPagerAdapter(this)
        binding.vpHome.adapter = homeSectionsPagerAdapter

        TabLayoutMediator(binding.tabHome, binding.vpHome) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f

        viewModel.getThemeSetting()?.observe(this) {isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.search -> true
            R.id.switch_theme -> {
                startActivity(Intent(this, SettingsActivity::class.java))
                true
            }
            else -> true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.search)?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    viewModel.searchGitHubUser(query)
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })

        return true
    }

    companion object {
        private val TAB_TITLES = intArrayOf(
            R.string.git_hub_search_result,
            R.string.git_hub_favourite
        )
    }
}