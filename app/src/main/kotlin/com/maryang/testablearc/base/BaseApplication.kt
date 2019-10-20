package com.maryang.testablearc.base

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.facebook.stetho.Stetho
import com.maryang.fastrxjava.util.ErrorHandler
import com.maryang.testablearc.event.EventBus
import com.maryang.testablearc.event.LogoutEvent
import com.maryang.testablearc.util.LoginHelper
import io.reactivex.plugins.RxJavaPlugins

class BaseApplication : Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var appContext: Context
        const val TAG = "GDG-TestableArchitecture"
    }

    private val loginHelper = LoginHelper()

    override fun onCreate() {
        super.onCreate()
        appContext = this
        Stetho.initializeWithDefaults(this)
        setErrorHanlder()
    }

    @SuppressLint("CheckResult")
    private fun setErrorHanlder() {
        RxJavaPlugins.setErrorHandler {
            ErrorHandler.globalHandle(it)
        }

        EventBus.observe().subscribe {
            when (it) {
                is LogoutEvent -> loginHelper.logout()
            }
        }
    }
}
