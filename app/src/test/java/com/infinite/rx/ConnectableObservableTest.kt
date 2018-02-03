package com.infinite.rx

import com.infinite.rx.challenge.week1.simpleObservable
import org.junit.Test

import org.junit.Assert.*

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
}
