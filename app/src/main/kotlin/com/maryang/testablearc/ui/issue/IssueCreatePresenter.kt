package com.maryang.testablearc.ui.issue

import com.maryang.testablearc.base.BasePresenter
import com.maryang.testablearc.domain.GithubRepository
import com.maryang.testablearc.entity.GithubRepo
import com.maryang.testablearc.event.DataObserver
import io.reactivex.android.schedulers.AndroidSchedulers

class IssueCreatePresenter(
    view: IssueCreateView,
    private val repository: GithubRepository
) : BasePresenter<IssueCreateView>(view) {

    fun createIssue(repo: GithubRepo, title: String, body: String) =
        repository.createIssue(repo.owner.userName, repo.name, title, body)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                DataObserver.post(it)
                view.onIssueCreated()
            }, {
                view.onIssueCreateFail()
            })
}
