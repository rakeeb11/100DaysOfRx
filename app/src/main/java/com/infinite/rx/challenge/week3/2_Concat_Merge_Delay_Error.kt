package com.infinite.rx.challenge.week3

import com.infinite.rx.challenge.print
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

val wholeStreams = Observable.range(1, 5)
        .flatMap { number ->
            if(number == 3) return@flatMap Observable.error<Throwable>(Throwable("Demo"))
            Observable.just(number).delay(100, TimeUnit.MILLISECONDS)
        }!!
val naturalStreams = Observable.fromIterable(listOf(0, 6, 10))
        .flatMap { number ->
            if(number == 6) return@flatMap Observable.error<Throwable>(Throwable("Demo"))
            Observable.just(number).delay(150, TimeUnit.MILLISECONDS)
        }!!

val mergedStream = Observable
        .mergeDelayError(wholeStreams, naturalStreams)
        .doOnError { "error: ${it.message}".print() }
        .doOnNext { "merge delay error on next: $it".print() }!!