package com.adhibuchori.finalsubmissionbfaa

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.adhibuchori.finalsubmissionbfaa.data.GitHubUserRepository
import com.adhibuchori.finalsubmissionbfaa.data.remote.response.ItemGitHubUser
import com.adhibuchori.finalsubmissionbfaa.ui.viewmodel.FavouriteViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`

class FavouriteViewModelTest {
    private lateinit var gitHubUserRepository: GitHubUserRepository
    private lateinit var viewModel: FavouriteViewModel

    private val dummy: LiveData<List<ItemGitHubUser>> = MutableLiveData(
        listOf(
            ItemGitHubUser(
                "adhibuchori",
                "user",
                "https://avatars.githubusercontent.com/u/79233136?v=4"
            )
        )
    )

    @Before
    fun before() {
        gitHubUserRepository = Mockito.mock(GitHubUserRepository::class.java)
        viewModel = FavouriteViewModel(gitHubUserRepository)
    }

    @Test
    fun getFavouriteUser() {
        `when`(viewModel.getFavouriteUser()).thenReturn(dummy)
        val listFav = viewModel.getFavouriteUser()
        Mockito.verify(gitHubUserRepository).getFavouriteGitHubUser()
        Assert.assertEquals(dummy.value, listFav.value)
    }
}