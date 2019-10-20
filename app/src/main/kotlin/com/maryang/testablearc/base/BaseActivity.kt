package com.maryang.testablearc.base

import androidx.appcompat.app.AppCompatActivity
import io.reactivex.disposables.CompositeDisposable

abstract class BaseActivity : AppCompatActivity() {

    protected val compositeDisposable = CompositeDisposable()

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

    override fun onSupportNavigateUp(): Boolean {
        if (!super.onSupportNavigateUp())
            onBackPressed()
        return true
    }
}

abstract class BasePresenterActivity<out T : BaseView> : BaseActivity() {
    abstract val presenter: BasePresenter<T>
}
