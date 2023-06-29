package com.adhibuchori.finalsubmissionbfaa.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.adhibuchori.finalsubmissionbfaa.data.Result
import com.adhibuchori.finalsubmissionbfaa.data.remote.response.ItemGitHubUser
import com.adhibuchori.finalsubmissionbfaa.databinding.FragmentFollowBinding
import com.adhibuchori.finalsubmissionbfaa.ui.activity.DetailActivity
import com.adhibuchori.finalsubmissionbfaa.ui.adapter.ListGitHubUserAdapter
import com.adhibuchori.finalsubmissionbfaa.ui.viewmodel.DetailViewModel
import com.adhibuchori.finalsubmissionbfaa.ui.viewmodel.ViewModelFactory

class FollowFragment : Fragment() {
    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DetailViewModel by activityViewModels {
        ViewModelFactory.getInstance(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        when (arguments?.getInt(ARG_SECTION_NUMBER, 0)) {
            0 -> viewModel.listGitHubUserFollower.observe(requireActivity()) { setAdapter(it) }
            1 -> viewModel.listGitHubUserFollowing.observe(requireActivity()) { setAdapter(it) }
        }

        binding.rvGitHubUser.apply {
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun setAdapter(result: Result<List<ItemGitHubUser>>?) {
        if (result != null) {
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.rvGitHubUser.adapter = ListGitHubUserAdapter(result.data) { user ->
                        val moveToDetail = Intent(requireActivity(), DetailActivity::class.java)
                        moveToDetail.putExtra(DetailActivity.EXTRA_GITHUB_USER, user)
                        startActivity(moveToDetail)
                    }
                }

                is Result.Error -> {
                    binding.progressBar.visibility = View.GONE
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