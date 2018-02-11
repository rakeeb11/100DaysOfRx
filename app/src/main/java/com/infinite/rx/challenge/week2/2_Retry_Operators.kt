package com.infinite.rx.challenge.week2

import com.infinite.rx.challenge.print
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function
import java.util.*
import java.util.concurrent.TimeUnit

val numberStreamWithRetry = Observable.create<Int> { emitter ->
    emitter.onNext(Random().nextInt())
    emitter.onError(Throwable("Demo"))
}
        .retry(2)

val numberStreamWithRetryWhen = Observable.create<Int> { emitter ->
    emitter.onNext(1)
    emitter.onError(Throwable("Demo"))
}
        .retryWhen {
            val range = Observable.range(1, 3)
            return@retryWhen it
                    .zipWith(range, BiFunction { _: Throwable, retryCount: Int -> retryCount })
                    .flatMap { retryCount ->
                        Observable.timer(Math.pow(1.5, retryCount.toDouble()).toLong(), TimeUnit.SECONDS)
                    }
                    .doOnNext {
                        "in retry when: $it at ${TimeUnit.NANOSECONDS.toSeconds(System.nanoTime())}".print()
                    }
        }
        .doOnNext { "from main stream: $it".print() }
