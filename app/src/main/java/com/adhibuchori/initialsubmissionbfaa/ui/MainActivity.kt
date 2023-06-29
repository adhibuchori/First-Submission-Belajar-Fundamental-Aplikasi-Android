package com.adhibuchori.initialsubmissionbfaa.ui

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.adhibuchori.initialsubmissionbfaa.adapter.ListGitHubUserAdapter
import com.adhibuchori.initialsubmissionbfaa.viewmodel.MainViewModel
import com.adhibuchori.initialsubmissionbfaa.R
import com.adhibuchori.initialsubmissionbfaa.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.listGitHubUserData.observe(this) {listGitHubUser ->
            binding.rvGitHubUser.adapter = ListGitHubUserAdapter(listGitHubUser) { gitHubUser ->
                val moveToDetail = Intent(this@MainActivity, DetailActivity::class.java)
                moveToDetail.putExtra(DetailActivity.EXTRA_GITHUB_USER, gitHubUser)
                startActivity(moveToDetail)
            }
        }

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }

        val layoutManager = LinearLayoutManager(this)
        binding.rvGitHubUser.layoutManager = layoutManager
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.search)?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.findGitHubUser(query)
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })

        return true
    }
}