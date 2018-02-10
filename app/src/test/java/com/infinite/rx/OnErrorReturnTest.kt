package com.infinite.rx

import com.infinite.rx.challenge.week1.gameStream
import org.junit.Test


class OnErrorReturnTest {

    @Test
    fun isValid() {

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
}

