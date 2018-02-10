package com.infinite.rx.challenge.week1

import android.annotation.SuppressLint
import android.support.annotation.VisibleForTesting
import io.reactivex.Observable
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import io.reactivex.observables.ConnectableObservable
import java.util.*

@SuppressLint("CheckResult")
/**
 * We can use the [Observable.publish] operator to force
 * [Subscription] in the absence of a [Subscriber]
 *
 * e.g. We can have an event which we'd want saved to our database first
 * regardless of it being subscribed to.
 */

/**
 * Extending on our example, [Observable]s are lazy by design
 * [Observable.doOnNext] will not be called as long as no one subscribes.
 *
 * Supposing that [getTweets] is an expensive http connection
 * each subscription will yield a new http connection; which is not ideal
 *
 * An idiomatic solution to this will be using [Observable.publish]
 * and [ConnectableObservable.connect] which used in unison will create
 * an artificial [Subscriber] immediately while keeping just one upstream
 * [Subscriber]
 * @see [com.infinite.rx#ConnectableObservableTest] in the test for more elaboration
 */
@VisibleForTesting
fun getTweetsWithDatabaseOperation() =
        getTweets()
                // on each notification the tweets are peeked into and stored
                .doOnNext {
                    println("writing to database from on next ...")
                    saveTweet(it)
                }!!


fun getTweets(): Observable<String> = Observable.create { emitter ->
    emitter.onNext(UUID.randomUUID().toString())
    emitter.onComplete()
}

fun saveTweet(tweet: String) {
    print("Saving tweet: $tweet")
}
