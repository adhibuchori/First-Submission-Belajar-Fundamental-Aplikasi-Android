package com.adhibuchori.finalsubmissionbfaa.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.adhibuchori.finalsubmissionbfaa.R
import com.adhibuchori.finalsubmissionbfaa.databinding.FragmentHomeBinding
import com.adhibuchori.finalsubmissionbfaa.ui.adapter.ListGitHubUserAdapter
import com.adhibuchori.finalsubmissionbfaa.ui.viewmodel.ViewModelFactory
import com.adhibuchori.finalsubmissionbfaa.data.Result
import com.adhibuchori.finalsubmissionbfaa.ui.activity.DetailActivity
import com.adhibuchori.finalsubmissionbfaa.ui.viewmodel.HomeViewModel
import com.google.android.material.snackbar.Snackbar

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by activityViewModels {
        ViewModelFactory.getInstance(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvGitHubUser.apply {
            layoutManager = LinearLayoutManager(context)
        }

        when (arguments?.getInt(ARG_SECTION_NUMBER, 0)) {
            0 -> viewModel.gitHubUserResult.observe(requireActivity()) { result ->
                if (result != null) {
                    when (result) {
                        is Result.Loading -> {
                            with(binding) {
                                tvDataIsEmpty.visibility = View.GONE
                                ivDataIsEmpty.visibility = View.GONE
                                progressBar.visibility = View.VISIBLE
                            }
                        }

                        is Result.Success -> {
                            with(binding) {
                                tvDataIsEmpty.visibility = View.GONE
                                ivDataIsEmpty.visibility = View.GONE
                                progressBar.visibility = View.GONE
                                rvGitHubUser.adapter =
                                    ListGitHubUserAdapter(result.data) { user ->
                                        val moveToDetail =
                                            Intent(requireActivity(), DetailActivity::class.java)
                                        moveToDetail.putExtra(
                                            DetailActivity.EXTRA_GITHUB_USER,
                                            user
                                        )
                                        startActivity(moveToDetail)
                                    }
                            }
                            if (result.data.isEmpty()) {
                                with(binding) {
                                    tvDataIsEmpty.visibility = View.VISIBLE
                                    ivDataIsEmpty.visibility = View.VISIBLE
                                    tvDataIsEmpty.text = getString(R.string.data_not_found)
                                    Snackbar.make(
                                        root,
                                        "Data not Found",
                                        Snackbar.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }

                        is Result.Error -> {
                            with(binding) {
                                tvDataIsEmpty.visibility = View.VISIBLE
                                ivDataIsEmpty.visibility = View.VISIBLE
                                progressBar.visibility = View.GONE
                                tvDataIsEmpty.text = getString(R.string.data_not_found)
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
            1 -> viewModel.getFavouriteUser().observe(requireActivity()) {
                if (it.isNotEmpty()) {
                    with(binding) {
                        tvDataIsEmpty.visibility = View.GONE
                        ivDataIsEmpty.visibility = View.GONE
                        rvGitHubUser.visibility = View.VISIBLE
                        rvGitHubUser.adapter = ListGitHubUserAdapter(it) { gitHubUser ->
                            val moveToDetail = Intent(requireActivity(), DetailActivity::class.java)
                            moveToDetail.putExtra(DetailActivity.EXTRA_GITHUB_USER, gitHubUser)
                            startActivity(moveToDetail)
                        }
                    }
                } else {
                    with(binding) {
                        tvDataIsEmpty.visibility = View.VISIBLE
                        ivDataIsEmpty.visibility = View.VISIBLE
                        rvGitHubUser.visibility = View.GONE
                        Snackbar.make(root, "Data is Empty", Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val ARG_SECTION_NUMBER = "section_number"
    }
}