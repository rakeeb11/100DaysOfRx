package com.infinite.rx.challenge.week1

import android.support.annotation.VisibleForTesting
import io.reactivex.Observable
import io.reactivex.functions.Cancellable

/**
 * ConnectableObservable can be used to coordinate between different
 * subscribers by sharing a single subscription.
 *
 * Maintaining a single subscription with publish().refCount()
 * [io.reactivex.Observable#refCount] and [io.reactivex.Observable#publish]
 *
 */

@VisibleForTesting
fun simpleObservable() = Observable.create<Int> { emitter ->
    println("Establishing connection ...")
    emitter.setCancellable({
        println("Disconnecting from previous subscriptions")
    })
    emitter.onNext(Math.random().toInt())
}!!