package com.infinite.rx

import com.infinite.rx.challenge.week1.*
import io.reactivex.Observable
import io.reactivex.observables.ConnectableObservable
import org.junit.Test
import java.util.concurrent.TimeUnit

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ConnectableObservableTest {

    @Test
    fun multipleSubscription() {
        // the code block showcases how subscribing to an
        // observable multiple times can trigger multiple connections
        // establishing connection is printed out twice ...
        val one = simpleObservable().test()
        "subscribed to one".print()
        val two = simpleObservable().test()
        "subscribed to two".print()

        one.assertSubscribed()
        two.assertSubscribed()

        one.dispose()
        "disposed one".print()
        two.dispose()
        "disposed two".print()

        assert(one.isDisposed)
        assert(two.isDisposed)
    }

    @Test
    fun multipleSubscriptionWithPublishAndRefCount() {
        // with publish and refcount
        // establishing connection is only printed out once!
        val lazy = simpleObservable().publish().refCount()
        // refCount is an operator that will count the active Subscribers
        // when the count goes from n to 0 refCount unsubscribes automatically
        "Before subscribers".print()
        val one = lazy.subscribe()
        "Subscribed to 1".print()
        val two = lazy.subscribe()
        "Subscribed to 2".print()
        one.dispose()
        "Disposed to 1".print()
        two.dispose()
        "Disposed to 2".print()

        // rather than using the operator publish and refCount
        // we can also go on to use share which will produce the same result
    }

    /**
     * [ConnectableObservable.publish] can be called on any [Observable]
     */
    @Test
    fun connectableObservableWithPublishAndConnect() {
        val tweetStream = getTweetsWithDatabaseOperation()
        // publish can be called on any observable
        val publisher = tweetStream.publish()

        // without call to connect our subscribers are put on hold
        // never directly subscribing to the upstream observable
        // however after a call to connect, a dedicated mediating
        // Subscriber subscribers to upstream Observable,
        // no matter how many downstream subscribers appeared before it

        // using a connectable observable like this, any hot observables
        // can be tracked allowing Subscribers to get the same sequence of notifications
        publisher.connect()
    }


    @Test
    fun autoConnectableObservables() {
        autoConnectConnectable
                .doOnSubscribe { "subscribed to first".print() }
                .doOnNext { "first:\t$it".print() }
                .subscribe()

        autoConnectConnectable
                .doOnSubscribe { "subscribed to second".print() }
                .doOnNext { "second:\t$it".print() }
                .subscribe()
    }

    @Test
    fun replayConnectableObservables() {

        val first = replayConnectable
                .doOnSubscribe { "subscribed to first".print() }
                .doOnNext { "first:\t$it".print() }
                .doOnComplete { "first completed".print() }
                .test()

        val second = replayConnectable
                .doOnSubscribe { "subscribed to second".print() }
                .doOnNext { "second:\t$it".print() }
                .doOnComplete { "second completed".print() }
                .delaySubscription(3, TimeUnit.SECONDS)
                .test()

        // check the first stream
        first
                .assertNoErrors()
                .assertComplete()
                .assertSubscribed()
                .assertValueSet(NUMBERS)

        // wait for the second stream to finish
        second.awaitTerminalEvent()

        // check the second stream
        second
                .assertNoErrors()
                .assertComplete()
                .assertSubscribed()
                .assertValueSet(NUMBERS)
    }
}
