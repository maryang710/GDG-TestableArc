package com.maryang.testablearc.data.repository

import com.google.gson.reflect.TypeToken
import com.maryang.testablearc.data.db.AppDatabase
import com.maryang.testablearc.data.request.CreateIssueRequest
import com.maryang.testablearc.data.source.ApiManager
import com.maryang.testablearc.domain.GithubRepository
import com.maryang.testablearc.entity.GithubRepo
import com.maryang.testablearc.entity.Issue
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers


class GithubRepository : GithubRepository {

    private val api = ApiManager.githubApi
    private val dao = AppDatabase.get().githubDao()

    override fun save(repo: GithubRepo) =
        dao.insert(repo)
            .subscribeOn(Schedulers.io())

    override fun searchGithubRepos(q: String): Single<List<GithubRepo>> =
        api.searchRepos(q)
            .map {
                it.asJsonObject.getAsJsonArray("items")
            }
            .map {
                val type = object : TypeToken<List<GithubRepo>>() {}.type
                ApiManager.gson.fromJson(it, type) as List<GithubRepo>
            }
            .subscribeOn(Schedulers.io())

    override fun checkStar(owner: String, repo: String): Completable =
        api.checkStar(owner, repo)
            .subscribeOn(Schedulers.io())

    override fun star(owner: String, repo: String): Completable =
        api.star(owner, repo)
            .subscribeOn(Schedulers.io())

    override fun unstar(owner: String, repo: String): Completable =
        api.unstar(owner, repo)
            .subscribeOn(Schedulers.io())

    override fun issues(owner: String, repo: String): Single<List<Issue>> =
        api.issues(owner, repo)
            .subscribeOn(Schedulers.io())

    override fun createIssue(
        owner: String,
        repo: String,
        title: String,
        body: String
    ): Single<Issue> =
        api.createIssue(owner, repo, CreateIssueRequest(title, body))
            .subscribeOn(Schedulers.io())
}
