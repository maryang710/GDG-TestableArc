package com.maryang.testablearc.ui.repos

import com.maryang.testablearc.base.BaseView
import com.maryang.testablearc.entity.GithubRepo

interface GithubReposView : BaseView {
    fun showNoInternetWarning()
    fun showLoading()
    fun hideLoading()
    fun showRepos(repos: List<GithubRepo>)
}
