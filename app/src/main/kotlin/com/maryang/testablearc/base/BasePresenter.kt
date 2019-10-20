package com.maryang.testablearc.base


abstract class BasePresenter<out T : BaseView>(protected val view: T)
