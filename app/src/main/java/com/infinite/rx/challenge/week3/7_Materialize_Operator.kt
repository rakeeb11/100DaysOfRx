package com.infinite.rx.challenge.week3

import io.reactivex.Observable

val materializeSource = Observable
        .fromIterable(listOf(1, 2, 3, 4, 5))
        .materialize()!!
