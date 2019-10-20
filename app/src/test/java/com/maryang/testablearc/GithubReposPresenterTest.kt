package com.maryang.testablearc

import com.maryang.testablearc.domain.GithubRepository
import com.maryang.testablearc.entity.GithubRepo
import com.maryang.testablearc.ui.repos.GithubReposPresenter
import com.maryang.testablearc.ui.repos.GithubReposView
import com.maryang.testablearc.util.NetworkHelper
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class GithubReposPresenterTest : BasePresenterTest() {

    private lateinit var presenter: GithubReposPresenter
    @Mock
    lateinit var view: GithubReposView
    @Mock
    lateinit var networkHelper: NetworkHelper
    @Mock
    lateinit var githubRepository: GithubRepository
    private val repos: List<GithubRepo> = emptyList()
    private val searchText = "searchText"

    override fun setUp() {
        super.setUp()
        presenter = GithubReposPresenter(
            view,
            networkHelper,
            githubRepository
        )

        Mockito.`when`(githubRepository.searchGithubRepos(Mockito.anyString()))
            .thenReturn(Single.just(repos))
        Mockito.`when`(githubRepository.checkStar(Mockito.anyString(), Mockito.anyString()))
            .thenReturn(Completable.complete())
    }

    @Test
    fun internetCheckTest() {
        Mockito.`when`(networkHelper.isConnected())
            .thenReturn(false)
        presenter.onCreate()
        Mockito.verify(view).showNoInternetWarning()
    }

    @Test
    fun searchTest() {
        presenter.searchGithubRepos(searchText)
        // view.showRepos(repo)가 동작하는가?
        Mockito.verify(view).showRepos(repos)
    }
}
