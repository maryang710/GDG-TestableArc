package com.maryang.testablearc.domain

import com.maryang.testablearc.entity.GithubRepo
import com.maryang.testablearc.entity.Issue
import io.reactivex.Completable
import io.reactivex.Single


interface GithubRepository {
    fun save(repo: GithubRepo): Completable
    fun searchGithubRepos(q: String): Single<List<GithubRepo>>
    fun checkStar(owner: String, repo: String): Completable
    fun star(owner: String, repo: String): Completable
    fun unstar(owner: String, repo: String): Completable
    fun issues(owner: String, repo: String): Single<List<Issue>>
    fun createIssue(owner: String, repo: String, title: String, body: String): Single<Issue>
}
