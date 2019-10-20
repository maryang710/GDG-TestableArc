package com.maryang.testablearc.event

import io.reactivex.BackpressureStrategy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject

object EventBus {
    private val bus = PublishSubject.create<Event>()

    fun post(event: Event) {
        bus.onNext(event)
    }

    fun observe() =
        bus.toFlowable(BackpressureStrategy.BUFFER)
            .observeOn(AndroidSchedulers.mainThread())
}
