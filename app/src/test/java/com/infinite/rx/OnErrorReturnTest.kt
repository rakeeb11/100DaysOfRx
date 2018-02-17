package com.infinite.rx

import com.infinite.rx.challenge.week1.gameStream
import com.infinite.rx.challenge.week2.ERROR_STREAM_MESSAGE
import com.infinite.rx.challenge.week2.NORMAL_STREAM_MESSAGE
import com.infinite.rx.challenge.week2.numberStreamsWithErrorResume
import com.infinite.rx.challenge.week2.numberStreamsWithExceptionResume
import org.junit.Test


class OnErrorReturnTest {

    @Test
    fun onErrorReturnTest() {

        val homeStream = gameStream
                .filter { it.home == "Home" }
                .test()

        val awayStream = gameStream
                .filter { it.away == "Away" }
                .test()

        homeStream
                .assertNoErrors()
                .assertNoValues()
                .assertComplete()

        awayStream
                .assertNoErrors()
                .assertNoValues()
                .assertComplete()
    }

    @Test
    fun onErrorResumeNextTest() {

        val stream = numberStreamsWithErrorResume
                .test()
        stream
                .assertValues(NORMAL_STREAM_MESSAGE, ERROR_STREAM_MESSAGE)
                .assertValueAt(0, NORMAL_STREAM_MESSAGE)
                .assertValueAt(1, ERROR_STREAM_MESSAGE)
                .assertComplete()
    }

    @Test
    fun onExceptionResumeNextTest() {

        val stream = numberStreamsWithExceptionResume
                .test()
        stream
                .assertValueAt(0, NORMAL_STREAM_MESSAGE)
                .assertErrorMessage("DEMO")
                .assertNotComplete()
    }
}

