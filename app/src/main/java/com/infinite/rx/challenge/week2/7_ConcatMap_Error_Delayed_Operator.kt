package com.infinite.rx.challenge.week2

import com.infinite.rx.challenge.print
import io.reactivex.Observable

val filterStream = Observable.fromIterable(listOf(1 , 2))
val tagStream =
        Observable.fromIterable(listOf("Quick", "Sort"))

val fusedStream = filterStream
        .concatMapDelayError { index ->
            tagStream
                    .flatMap { tag ->
                        if (index == 1 && tag == "Sort") return@flatMap Observable.error<Throwable> { Throwable("Demo") }
                        Observable.just("$index & $tag")
                    }
        }
        .doOnNext { "$it received".print() }
        .doOnError { "found error: ${it.message}" }!!