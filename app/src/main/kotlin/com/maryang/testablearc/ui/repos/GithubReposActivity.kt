package com.maryang.testablearc.ui.repos

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.maryang.testablearc.R
import com.maryang.testablearc.base.BasePresenterActivity
import com.maryang.testablearc.data.repository.GithubRepository
import com.maryang.testablearc.entity.GithubRepo
import com.maryang.testablearc.event.DataObserver
import com.maryang.testablearc.util.NetworkHelper
import io.reactivex.rxkotlin.plusAssign
import kotlinx.android.synthetic.main.activity_github_repos.*
import org.jetbrains.anko.toast


class GithubReposActivity : BasePresenterActivity<GithubReposView>(),
    GithubReposView {

    override val presenter: GithubReposPresenter by lazy {
        GithubReposPresenter(
            this,
            NetworkHelper(this),
            GithubRepository()
        )
    }
    private val adapter: GithubReposAdapter by lazy {
        GithubReposAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_github_repos)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = this.adapter
        refreshLayout.setOnRefreshListener { presenter.searchGithubRepos() }
        searchText.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(text: Editable?) {
                presenter.searchGithubRepos(text.toString())
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
        subscribeDataObserver()
        presenter.onCreate()
    }

    private fun subscribeDataObserver() {
        compositeDisposable += DataObserver.observe()
            .filter { it is GithubRepo }
            .subscribe { repo ->
                adapter.items.find {
                    it.id == repo.id
                }?.apply {
                    star = star.not()
                }
                adapter.notifyDataSetChanged()
            }
    }

    override fun showNoInternetWarning() {
        toast(R.string.warn_internet_no)
        finish()
    }

    override fun showLoading() {
        loading.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        loading.visibility = View.GONE
        refreshLayout.isRefreshing = false
    }

    override fun showRepos(repos: List<GithubRepo>) {
        adapter.items = repos
    }
}
