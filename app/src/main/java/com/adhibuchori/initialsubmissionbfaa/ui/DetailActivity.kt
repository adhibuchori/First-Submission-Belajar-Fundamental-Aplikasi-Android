package com.adhibuchori.initialsubmissionbfaa.ui

import android.content.ContentValues.TAG
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import com.adhibuchori.initialsubmissionbfaa.R
import com.adhibuchori.initialsubmissionbfaa.adapter.SectionsPagerAdapter
import com.adhibuchori.initialsubmissionbfaa.databinding.ActivityDetailBinding
import com.adhibuchori.initialsubmissionbfaa.response.ItemsItem
import com.adhibuchori.initialsubmissionbfaa.viewmodel.DetailViewModel
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        @Suppress("DEPRECATION")
        val gitHubUser = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(EXTRA_GITHUB_USER, ItemsItem::class.java)
        } else {
            intent.getParcelableExtra(EXTRA_GITHUB_USER)
        }

        if (gitHubUser != null && viewModel.detailGitHubUserData.value == null) {
            viewModel.getGitHubUser(gitHubUser.login)
        }

        viewModel.detailGitHubUserData.observe(this) {
            Log.i(TAG, "onCreate: ${gitHubUser?.login}")
            with(binding) {
                Glide.with(this@DetailActivity)
                    .load(it.avatarUrl)
                    .into(imgItemGitHubUser)
                tvItemName.text = it.name
                tvItemUsername.text = it.login
                tvItemType.text = it.type
                tvItemRepositoryValue.text = it.publicRepos.toString()
                tvItemFollowersValue.text = it.followers.toString()
                tvItemFollowingValue.text = it.following.toString()
            }

            val setActionBar = supportActionBar
            setActionBar?.title = it.login
            setActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        binding.vpFollow.adapter = sectionsPagerAdapter

        TabLayoutMediator(binding.tabFollow, binding.vpFollow) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onSupportNavigateUp(): Boolean {
        this.onBackPressedDispatcher.onBackPressed()
        return true
    }

    companion object {
        const val EXTRA_GITHUB_USER = "extra_github_user"

        private val TAB_TITLES = intArrayOf(
            R.string.git_hub_followers,
            R.string.git_hub_following
        )
    }
}