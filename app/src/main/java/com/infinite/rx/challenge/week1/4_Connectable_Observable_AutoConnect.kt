package com.infinite.rx.challenge.week1

import io.reactivex.Observable

val connectable by lazy {
    Observable.create<Long> {
        println("initializing observable ...")
        it.setCancellable { println("killing observable ...") }
        it.onNext(System.nanoTime())
        it.onComplete()
    }
            .publish().autoConnect(2)
}
