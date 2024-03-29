package com.maryang.testablearc.data.source

import com.google.gson.JsonElement
import com.maryang.testablearc.data.request.CreateIssueRequest
import com.maryang.testablearc.entity.Issue
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.*

interface GithubApi {

    @GET("search/repositories")
    fun searchRepos(
        @Query("q") search: String
    ): Single<JsonElement>

    @GET("user/starred/{owner}/{repo}")
    fun checkStar(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): Completable

    @PUT("user/starred/{owner}/{repo}")
    fun star(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): Completable

    @DELETE("user/starred/{owner}/{repo}")
    fun unstar(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): Completable

    @GET("/repos/{owner}/{repo}/issues")
    fun issues(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): Single<List<Issue>>

    @POST("/repos/{owner}/{repo}/issues")
    fun createIssue(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Body request: CreateIssueRequest
    ): Single<Issue>
}
