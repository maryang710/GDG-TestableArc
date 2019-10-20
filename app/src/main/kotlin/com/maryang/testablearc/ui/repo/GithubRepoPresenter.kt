package com.maryang.testablearc.ui.repo

import android.annotation.SuppressLint
import com.maryang.testablearc.base.BasePresenter
import com.maryang.testablearc.domain.GithubRepository
import com.maryang.testablearc.entity.GithubRepo
import com.maryang.testablearc.event.DataObserver
import io.reactivex.android.schedulers.AndroidSchedulers

class GithubRepoPresenter(
    view: GithubRepoView,
    private val repository: GithubRepository
) : BasePresenter<GithubRepoView>(view) {

    private lateinit var repo: GithubRepo

    fun onCreate(repo: GithubRepo) {
        this.repo = repo
        view.showRepo(repo)
        save(repo)
        issues(repo)
    }

    private fun save(repo: GithubRepo) =
        repository.save(repo)
            .subscribe()

    private fun issues(repo: GithubRepo) =
        repository.issues(repo.owner.userName, repo.name)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { view.showIssues(it) },
                { it.printStackTrace() }
            )

    @SuppressLint("CheckResult")
    fun onClickStar() {
        val originalStar = repo.star
        view.showStar(!originalStar)
        view.showStarCount(repo.stargazersCount.let {
            if (originalStar) it - 1 else it + 1
        })

        (if (originalStar) repository.unstar(repo.owner.userName, repo.name)
        else repository.star(repo.owner.userName, repo.name))
            .doOnComplete {
                repo.apply {
                    star = !originalStar
                    stargazersCount =
                        if (originalStar) stargazersCount - 1
                        else stargazersCount + 1
                }
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                DataObserver.post(repo)
            }, {
                it.printStackTrace()
                view.showStar(originalStar)
                view.showStarCount(repo.stargazersCount)
            })
    }

    fun onClickIssueCreate() {
        view.startIssueCreate(repo)
    }
}
