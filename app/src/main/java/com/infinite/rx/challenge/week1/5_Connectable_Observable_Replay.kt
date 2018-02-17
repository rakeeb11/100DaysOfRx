package com.infinite.rx.challenge.week1

import com.infinite.rx.challenge.print
import io.reactivex.Observable
import io.reactivex.subjects.ReplaySubject

val NUMBERS = listOf(1, 2, 3, 4, 5)

val replayConnectable: Observable<Int> by lazy {
    Observable.create<Int> { emitter ->
        "initializing stream ...".print()
        emitter.setCancellable { "destroying stream ...".print() }
        NUMBERS.forEach {
            emitter.onNext(it)
        }
        emitter.onComplete()
    }
            .replay()
            .autoConnect()
}


val replaySubject by lazy {
    ReplaySubject.create<Int>()
}