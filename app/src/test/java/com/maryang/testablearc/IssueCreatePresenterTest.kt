package com.maryang.testablearc

import com.maryang.testablearc.domain.GithubRepository
import com.maryang.testablearc.entity.GithubRepo
import com.maryang.testablearc.entity.Issue
import com.maryang.testablearc.ui.issue.IssueCreatePresenter
import com.maryang.testablearc.ui.issue.IssueCreateView
import io.reactivex.Single
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class IssueCreatePresenterTest : BasePresenterTest() {

    private lateinit var presenter: IssueCreatePresenter
    @Mock
    lateinit var view: IssueCreateView
    @Mock
    lateinit var githubRepository: GithubRepository
    private val repo = GithubRepo()
    private val issue = Issue()

    override fun setUp() {
        super.setUp()
        presenter = IssueCreatePresenter(
            view,
            githubRepository
        )
        Mockito.`when`(
            githubRepository.createIssue(
                anyString(),
                anyString(),
                anyString(),
                anyString()
            )
        ).thenReturn(Single.just(issue))
    }

    @Test
    fun createIssueTest() {
        presenter.createIssue(repo, "title", "body")
        Mockito.verify(view).onIssueCreated()
    }
}
