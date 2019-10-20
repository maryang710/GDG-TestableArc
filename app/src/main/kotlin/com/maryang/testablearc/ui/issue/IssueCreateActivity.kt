package com.maryang.testablearc.ui.issue

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.textChanges
import com.maryang.testablearc.R
import com.maryang.testablearc.base.BasePresenterActivity
import com.maryang.testablearc.data.repository.GithubRepository
import com.maryang.testablearc.entity.GithubRepo
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_issue_create.*
import org.jetbrains.anko.backgroundColorResource
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import java.util.concurrent.TimeUnit

class IssueCreateActivity : BasePresenterActivity<IssueCreateView>(),
    IssueCreateView {

    companion object {
        private const val KEY_REPO = "KEY_REPO"

        fun start(context: Context, repo: GithubRepo) {
            context.startActivity(
                context.intentFor<IssueCreateActivity>(
                    KEY_REPO to repo
                )
            )
        }
    }

    override val presenter: IssueCreatePresenter by lazy {
        IssueCreatePresenter(this, GithubRepository())
    }
    private lateinit var repo: GithubRepo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_issue_create)
        supportActionBar?.run {
            title = "이슈 생성"
            setDisplayHomeAsUpEnabled(true)
        }
        this.repo = intent.getParcelableExtra(KEY_REPO)
        setEditText()
        setOnClickListener()
    }

    @SuppressLint("CheckResult")
    private fun setEditText() {
        Observable.merge(
            titleText.textChanges(), bodyText.textChanges()
        ).subscribe {
            enableComplete(it.isNotEmpty())
        }
    }

    private fun enableComplete(enable: Boolean) {
        complete.isEnabled = enable
        complete.backgroundColorResource =
            if (enable) R.color.colorPrimary else R.color.grey_500
    }

    @SuppressLint("CheckResult")
    private fun setOnClickListener() {
        complete.clicks()
            .throttleFirst(1, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                presenter.createIssue(
                    repo,
                    titleText.text.toString(),
                    bodyText.text.toString()
                )
            }
    }

    override fun onIssueCreated() {
        toast(R.string.issue_create_complete)
        finish()
    }

    override fun onIssueCreateFail() {
        toast(R.string.issue_create_fail)
        finish()
    }
}
