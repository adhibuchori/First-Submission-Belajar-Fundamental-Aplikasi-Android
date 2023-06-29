package com.adhibuchori.finalsubmissionbfaa.ui.activity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.adhibuchori.finalsubmissionbfaa.R
import com.adhibuchori.finalsubmissionbfaa.data.remote.response.ItemGitHubUser
import com.adhibuchori.finalsubmissionbfaa.databinding.ActivityDetailBinding
import com.adhibuchori.finalsubmissionbfaa.ui.adapter.FollowSectionsPagerAdapter
import com.adhibuchori.finalsubmissionbfaa.ui.viewmodel.DetailViewModel
import com.adhibuchori.finalsubmissionbfaa.ui.viewmodel.ViewModelFactory
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import com.adhibuchori.finalsubmissionbfaa.data.Result

class DetailActivity : AppCompatActivity() {

    private var _binding: ActivityDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<DetailViewModel> {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        @Suppress("DEPRECATION") val gitHubUser = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(EXTRA_GITHUB_USER, ItemGitHubUser::class.java)
        } else {
            intent.getParcelableExtra(EXTRA_GITHUB_USER)
        }

        val followSectionsPagerAdapter = FollowSectionsPagerAdapter(this)
        binding.vpFollow.adapter = followSectionsPagerAdapter

        TabLayoutMediator(binding.tabFollow, binding.vpFollow) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f

        if (gitHubUser != null && viewModel.gitHubUserDetail.value == null) {

            viewModel.getGitHubUser(gitHubUser.login)

            viewModel.getFavoriteUserByLogin(gitHubUser.login).observe(this) {
                if (it == null) {
                    binding.fabFavourite.setImageResource(R.drawable.ic_favorite_border)
                    binding.fabFavourite.setOnClickListener {
                        viewModel.addFavouriteUser(gitHubUser)
                        Snackbar.make(
                            binding.root,
                            "${gitHubUser.login} added to favourite",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    binding.fabFavourite.setImageResource(R.drawable.ic_favorite)
                    binding.fabFavourite.setOnClickListener {
                        viewModel.deleteFavouriteUser(gitHubUser.login)
                        Snackbar.make(
                            binding.root,
                            "${gitHubUser.login} deleted from favourite",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

        viewModel.gitHubUserDetail.observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        with(binding) {
                            progressBar.visibility = View.VISIBLE
                            fabShare.setOnClickListener {
                                Snackbar.make(
                                    root,
                                    "Data is loading...",
                                    Snackbar.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }

                    is Result.Success -> {
                        Glide.with(this).load(result.data.avatarUrl).into(binding.imgItemGitHubUser)

                        with(binding) {
                            progressBar.visibility = View.GONE
                            tvItemUsername.text = result.data.login
                            tvItemName.text = result.data.name
                            tvItemType.text = result.data.type
                            tvItemRepositoryValue.text = result.data.publicRepos.toString()
                            tvItemFollowersValue.text = result.data.followers.toString()
                            tvItemFollowingValue.text = result.data.following.toString()
                            fabShare.setOnClickListener {
                                val shareIntent = Intent(Intent.ACTION_SEND)
                                shareIntent.type = "text/plain"
                                shareIntent.putExtra(
                                    Intent.EXTRA_TEXT,
                                    "Check out ${result.data.login} GitHub Account" +
                                            "\n\n 📍 Account Link" +
                                            "\n${result.data.htmlUrl}"
                                )
                                startActivity(shareIntent)
                            }
                        }

                        val setActionBar = supportActionBar
                        setActionBar?.title = result.data.login
                        setActionBar?.setDisplayHomeAsUpEnabled(true)
                    }

                    is Result.Error -> {
                        with(binding) {
                            progressBar.visibility = View.GONE
                            Snackbar.make(
                                root,
                                "Error getting data: ${result.error}",
                                Snackbar.LENGTH_SHORT
                            ).show()
                            fabShare.setOnClickListener {
                                Snackbar.make(
                                    root,
                                    "Error getting data: ${result.error}",
                                    Snackbar.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
            }
        }
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