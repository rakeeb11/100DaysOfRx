package com.infinite.rx

import com.infinite.rx.challenge.print
import com.infinite.rx.challenge.week2.numberStreamWithRetry
import com.infinite.rx.challenge.week2.numberStreamWithRetryWhen
import io.reactivex.Observable
import io.reactivex.functions.Consumer
import org.junit.Test
import java.util.concurrent.TimeUnit

class RetryTest {

    @Test
    fun retryNumberStream() {
        val stream = numberStreamWithRetry
                .doOnNext { println(it) }
                .test()

        stream
                .assertSubscribed()
                .assertValueCount(3)
                .assertErrorMessage("Demo")
                .assertNotComplete()
    }

    @Test
    fun retryWhenNumberStream() {
        val stream = numberStreamWithRetryWhen
                .test()
        stream.awaitTerminalEvent()
        stream
                .assertValueCount(4)
                .assertSubscribed()
                .assertComplete()
    }
}