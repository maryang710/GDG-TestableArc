package com.maryang.testablearc.ui.repos

import android.annotation.SuppressLint
import com.maryang.testablearc.base.BasePresenter
import com.maryang.testablearc.domain.GithubRepository
import com.maryang.testablearc.util.NetworkHelper
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class GithubReposPresenter(
    view: GithubReposView,
    private val networkHelper: NetworkHelper,
    private val repository: GithubRepository
) : BasePresenter<GithubReposView>(view) {

    private val searchSubject = PublishSubject.create<Pair<String, Boolean>>()
    private var searchText = ""

    init {
        subscribeSearchSubject()
    }

    fun onCreate() {
        if (networkHelper.isConnected().not())
            view.showNoInternetWarning()
    }

    fun searchGithubRepos(search: String) {
        searchSubject.onNext(search to true)
    }

    fun searchGithubRepos() {
        searchSubject.onNext(searchText to false)
    }

    @SuppressLint("CheckResult")
    private fun subscribeSearchSubject() {
        searchSubject
            .debounce(400, TimeUnit.MILLISECONDS, Schedulers.io())
            .doOnNext { searchText = it.first }
            .map { it.second }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { if (it) view.showLoading() }
            .observeOn(Schedulers.io())
            .flatMapSingle {
                repository.searchGithubRepos(searchText)
                    .flatMap {
                        Completable.merge(
                            it.map { repo ->
                                repository.checkStar(repo.owner.userName, repo.name)
                                    .doOnComplete { repo.star = true }
                                    .onErrorComplete()
                            }
                        ).toSingleDefault(it)
                    }
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view.hideLoading()
                view.showRepos(it)
            }, {
                it.printStackTrace()
                view.hideLoading()
            })
    }
}
