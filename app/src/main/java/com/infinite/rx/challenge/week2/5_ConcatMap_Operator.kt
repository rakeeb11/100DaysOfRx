package com.infinite.rx.challenge.week2

import java.util.concurrent.TimeUnit

val userConcatMapStream = userIdStream
        .concatMap {
            if (it % 2 == 0) {
                return@concatMap getUser(it, TimeUnit.NANOSECONDS.toSeconds(System.nanoTime()))
                        .delay(1, TimeUnit.SECONDS)
            }
            getUser(it, TimeUnit.NANOSECONDS.toSeconds(System.nanoTime()))
        }
        .doOnNext { println(it) }