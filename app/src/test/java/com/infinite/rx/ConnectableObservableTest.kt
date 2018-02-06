package com.infinite.rx

import com.infinite.rx.challenge.week1.connectable
import com.infinite.rx.challenge.week1.simpleObservable
import com.infinite.rx.challenge.week1.getTweetsWithDatabaseOperation
import io.reactivex.Observable
import io.reactivex.observables.ConnectableObservable
import org.junit.Test

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
        val one = simpleObservable().subscribe()
        println("subscribed to one")
        val two = simpleObservable().subscribe()
        println("subscribed to two")
        one.dispose()
        println("disposed one")
        two.dispose()
        println("disposed two")
    }

    @Test
    fun multipleSubscriptionWithPublishAndRefCount() {
        // with publish and refcount
        // establishing connection is only printed out once!
        val lazy = simpleObservable().publish().refCount()
        // refCount is an operator that will count the active Subscribers
        // when the count goes from n to 0 refCount unsubscribes automatically
        println("Before subscribers")
        val one = lazy.subscribe()
        println("Subscribed to 1")
        val two = lazy.subscribe()
        println("Subscribed to 2")
        one.dispose()
        println("Disposed to 1")
        two.dispose()
        println("Disposed to 2")

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
        connectable
                .doOnSubscribe { println("subscribed to first") }
                .doOnNext { println("first:\t$it") }
                .subscribe()

        connectable
                .doOnSubscribe { println("subscribed to second") }
                .doOnNext { println("second:\t$it") }
                .subscribe()
    }
}
