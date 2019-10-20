package com.maryang.testablearc

import com.maryang.testablearc.domain.GithubRepository
import com.maryang.testablearc.entity.GithubRepo
import com.maryang.testablearc.entity.Issue
import com.maryang.testablearc.ui.repo.GithubRepoPresenter
import com.maryang.testablearc.ui.repo.GithubRepoView
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Assert
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class GithubRepoPresenterTest : BasePresenterTest() {

    private lateinit var presenter: GithubRepoPresenter
    @Mock
    lateinit var view: GithubRepoView
    @Mock
    lateinit var githubRepository: GithubRepository
    private val repo = GithubRepo()
    private val issues = emptyList<Issue>()

    override fun setUp() {
        super.setUp()
        presenter = GithubRepoPresenter(
            view,
            githubRepository
        )
        `when`(githubRepository.save(repo))
            .thenReturn(Completable.complete())
        `when`(githubRepository.issues(anyString(), anyString()))
            .thenReturn(Single.just(issues))
        `when`(githubRepository.star(anyString(), anyString()))
            .thenReturn(Completable.complete())
        `when`(githubRepository.unstar(anyString(), anyString()))
            .thenReturn(Completable.complete())
    }

    @Test
    fun onCreateTest() {
        presenter.onCreate(repo)
        verify(view).showRepo(repo)
        verify(view).showIssues(issues)
    }

    @Test
    fun clickStarTest() {
        repo.star = false
        presenter.onCreate(repo)
        val originalStarCount = repo.stargazersCount
        presenter.onClickStar()
        verify(view).showStar(true)
        verify(view).showStarCount(originalStarCount + 1)
        Assert.assertEquals(true, repo.star)
        Assert.assertEquals(originalStarCount + 1, repo.stargazersCount)
    }

    @Test
    fun clickUnstarTest() {
        repo.star = true
        presenter.onCreate(repo)
        val originalStarCount = repo.stargazersCount
        presenter.onClickStar()
        verify(view).showStar(false)
        verify(view).showStarCount(originalStarCount - 1)
        Assert.assertEquals(false, repo.star)
        Assert.assertEquals(originalStarCount - 1, repo.stargazersCount)
    }

    @Test
    fun clickStarErrorTest() {
        `when`(githubRepository.star(anyString(), anyString()))
            .thenReturn(Completable.error(Exception()))
        repo.star = false
        presenter.onCreate(repo)
        val originalStarCount = repo.stargazersCount
        presenter.onClickStar()
        verify(view).showStar(true)
        verify(view).showStar(false)
        verify(view).showStarCount(originalStarCount + 1)
        verify(view).showStarCount(originalStarCount)
        Assert.assertEquals(false, repo.star)
        Assert.assertEquals(originalStarCount, repo.stargazersCount)
    }
}
