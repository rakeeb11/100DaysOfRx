package com.infinite.rx.challenge.week4

import com.infinite.rx.challenge.print
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

val scanningSequence = Observable.fromIterable(listOf(1, 1, 2, 3, 5, 5))
        .scan(Pair(0, false), { lastEmission: Pair<Int, Boolean>, current: Int ->
            if(lastEmission.first != current) return@scan Pair(current, false)
            return@scan Pair(current, true)
        })
        .flatMap {
            if(it.second) {
                return@flatMap Observable.just(it.first)
                        .throttleFirst(2, TimeUnit.SECONDS)
            }
            return@flatMap Observable.just(it.first)
        }
        .doOnNext { "on next: $it".print() }

