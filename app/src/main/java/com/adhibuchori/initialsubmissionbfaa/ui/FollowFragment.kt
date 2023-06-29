package com.adhibuchori.initialsubmissionbfaa.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.adhibuchori.initialsubmissionbfaa.adapter.ListGitHubUserAdapter
import com.adhibuchori.initialsubmissionbfaa.databinding.FragmentFollowBinding
import com.adhibuchori.initialsubmissionbfaa.response.ItemsItem
import com.adhibuchori.initialsubmissionbfaa.viewmodel.DetailViewModel

class FollowFragment : Fragment() {

    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DetailViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.isLoadingFollow.observe(requireActivity()) {
            showLoading(it)
        }

        when (arguments?.getInt(ARG_SECTION_NUMBER, 0)) {
            0 -> viewModel.gitHubUserFollower.observe(requireActivity()) { setAdapter(it) }
            1 -> viewModel.gitHubUserFollowing.observe(requireActivity()) { setAdapter(it) }
        }

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvGitHubUser.layoutManager = layoutManager
    }

    private fun setAdapter(listGitHubUser: List<ItemsItem>) {
        binding.rvGitHubUser.adapter = ListGitHubUserAdapter(listGitHubUser) { gitHubUser ->
            val moveToDetail = Intent(requireActivity(), DetailActivity::class.java)
            moveToDetail.putExtra(DetailActivity.EXTRA_GITHUB_USER, gitHubUser)
            startActivity(moveToDetail)

        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val ARG_SECTION_NUMBER = "section_number"
    }

}