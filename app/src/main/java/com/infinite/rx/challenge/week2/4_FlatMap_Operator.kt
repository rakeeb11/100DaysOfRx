package com.infinite.rx.challenge.week2

import io.reactivex.Observable
import java.util.concurrent.TimeUnit

fun getUser(id: Int) = Observable
        .fromCallable { User(id) }

val userFlatMapStream = userIdStream
        .flatMap {
            if (it % 2 == 0) {
                return@flatMap getUser(it)
                        .delay(100, TimeUnit.MILLISECONDS)
            }
            getUser(it)
        }
        .doOnNext { println(it) }