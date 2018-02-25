package com.infinite.rx.challenge.week3

import com.infinite.rx.challenge.print
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

val firstStream = Observable.fromIterable(listOf(1, 2, 3))
        .flatMap { item ->
            val stream = Observable.just(item)
            if(item % 2 == 0) return@flatMap stream
            stream.delay(1, TimeUnit.SECONDS)
        }

val secondStream = Observable.fromIterable(listOf(4, 5, 6))
        .flatMap { item ->
            val stream = Observable.just(item)
            if(item % 2 == 0) return@flatMap stream
            stream.delay(100, TimeUnit.MILLISECONDS)
        }

val concatStream = Observable.concat(firstStream, secondStream)
        .doOnNext { "concat result: $it".print() }

val mergeStream = Observable.merge(firstStream, secondStream)
        .doOnNext { "merge result: $it".print() }