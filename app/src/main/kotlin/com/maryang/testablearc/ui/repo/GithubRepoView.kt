package com.maryang.testablearc.ui.repo

import com.maryang.testablearc.base.BaseView
import com.maryang.testablearc.entity.GithubRepo
import com.maryang.testablearc.entity.Issue

interface GithubRepoView : BaseView {
    fun showRepo(repo: GithubRepo)
    fun showIssues(issues: List<Issue>)
    fun showStar(show: Boolean)
    fun showStarCount(count: Int)
    fun startIssueCreate(repo: GithubRepo)
}
