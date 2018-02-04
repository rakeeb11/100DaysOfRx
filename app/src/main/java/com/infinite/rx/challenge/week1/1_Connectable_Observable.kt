package com.infinite.rx.challenge.week1

import android.support.annotation.VisibleForTesting
import io.reactivex.Observable
import io.reactivex.observables.ConnectableObservable

/**
 * ConnectableObservable can be used to coordinate between different
 * subscribers by sharing a single subscription.
 *
 * Maintaining a single subscription with publish().refCount()
 * [ConnectableObservable.refCount] and [Observable.publish]
 */

@VisibleForTesting
fun simpleObservable() = Observable.create<Int> { emitter ->
    println("Establishing connection ...")
    emitter.setCancellable({
        println("Disconnecting from previous subscriptions")
    })
    emitter.onNext(Math.random().toInt())
}!!