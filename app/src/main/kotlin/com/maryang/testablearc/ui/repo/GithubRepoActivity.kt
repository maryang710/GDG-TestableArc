package com.maryang.testablearc.ui.repo

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.maryang.testablearc.R
import com.maryang.testablearc.base.BasePresenterActivity
import com.maryang.testablearc.data.repository.GithubRepository
import com.maryang.testablearc.entity.GithubRepo
import com.maryang.testablearc.entity.Issue
import com.maryang.testablearc.event.DataObserver
import com.maryang.testablearc.ui.issue.IssueCreateActivity
import io.reactivex.rxkotlin.plusAssign
import kotlinx.android.synthetic.main.activity_github_repo.*
import org.jetbrains.anko.imageResource
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.sdk21.listeners.onClick


class GithubRepoActivity : BasePresenterActivity<GithubRepoView>(),
    GithubRepoView {

    companion object {
        private const val KEY_REPO = "KEY_REPO"

        fun start(context: Context, repo: GithubRepo) {
            context.startActivity(
                context.intentFor<GithubRepoActivity>(
                    KEY_REPO to repo
                )
            )
        }
    }

    override val presenter: GithubRepoPresenter by lazy {
        GithubRepoPresenter(this, GithubRepository())
    }
    private val issuesAdapter: IssuesAdapter by lazy {
        IssuesAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_github_repo)
        intent.getParcelableExtra<GithubRepo>(KEY_REPO)?.let {
            supportActionBar?.run {
                title = it.name
                setDisplayHomeAsUpEnabled(true)
            }
            presenter.onCreate(it)
        } ?: finish()
        star.onClick { presenter.onClickStar() }
        subscribeDataObserver()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_repo, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item!!.itemId) {
            R.id.create_issue -> {
                presenter.onClickIssueCreate()
                true
            }
            else ->
                super.onOptionsItemSelected(item)
        }
    }

    private fun subscribeDataObserver() {
        compositeDisposable += DataObserver.observe()
            .filter { it is Issue }
            .subscribe { issue ->
                issuesAdapter.items.find {
                    it.id == issue.id
                } ?: run {
                    issuesAdapter.addItem(issue as Issue)
                    issues.scrollToPosition(0)
                }
            }
    }

    override fun showRepo(repo: GithubRepo) {
        Glide.with(this)
            .load(repo.owner.avatarUrl)
            .into(ownerImage)
        ownerName.text = repo.owner.userName
        starCount.text = repo.stargazersCount.toString()
        watcherCount.text = repo.watchersCount.toString()
        forksCount.text = repo.forksCount.toString()
        showStar(repo.star)

        issues.layoutManager = LinearLayoutManager(this)
        issues.adapter = issuesAdapter
    }

    override fun showStar(show: Boolean) {
        star.imageResource =
            if (show) R.drawable.baseline_star_24 else R.drawable.baseline_star_border_24
    }

    override fun showStarCount(count: Int) {
        starCount.text = count.toString()
    }

    override fun showIssues(issues: List<Issue>) {
        issueLabel.visibility = View.VISIBLE
        issuesAdapter.items = issues.toMutableList()
    }

    override fun startIssueCreate(repo: GithubRepo) {
        IssueCreateActivity.start(this, repo)
    }
}
