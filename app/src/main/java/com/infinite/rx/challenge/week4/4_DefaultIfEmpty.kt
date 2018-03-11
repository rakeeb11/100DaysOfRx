package com.infinite.rx.challenge.week4

import io.reactivex.Observable

val emptyStream = Observable.empty<Int>()
        .defaultIfEmpty(10)

val streamWithDefaultValue = Observable.range(1, 5)
        .defaultIfEmpty(10)

val streamWithFilterAndDefaultValue = Observable.range(1, 5)
        .filter { it > 9 }
        .defaultIfEmpty(11)