package com.infinite.rx.challenge.week3

import com.infinite.rx.challenge.print
import io.reactivex.Observable
import java.util.concurrent.TimeUnit


fun stream(initialDelay: Long, interval: Long, name: String) =
        Observable
                .interval(initialDelay, interval, TimeUnit.MILLISECONDS)
                .map { delay -> "$name: $delay" }
                .doOnNext { "on next for: $name, with value $it".print() }
                .doOnComplete { "on complete: $name".print() }
                .doOnSubscribe { "Subscribed to $name".print() }
                .doOnDispose { "Disposed $name".print() }!!

