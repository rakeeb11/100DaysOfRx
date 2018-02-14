package com.infinite.rx

import com.infinite.rx.challenge.week1.simpleMapping
import com.infinite.rx.challenge.week2.User
import com.infinite.rx.challenge.week2.userConcatMapStream
import com.infinite.rx.challenge.week2.userFlatMapStream
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.BlockJUnit4ClassRunner

@RunWith(BlockJUnit4ClassRunner::class)
class OperatorsTest {

    @Test
    fun oneOnOneMapping() {
        simpleMapping()
                .doOnNext { println(it) }
                .subscribe()
    }

    @Test
    fun flatMapping() {
        val flattenedStream = userFlatMapStream
                .test()
        flattenedStream.awaitTerminalEvent()
        flattenedStream
                .assertComplete()
                .assertValueAt(0, User(1))
                .assertValueAt(1, User(3))
    }

    @Test
    fun concatMapping() {
        val flattenedStream = userConcatMapStream
                .test()
        flattenedStream.awaitTerminalEvent()
        flattenedStream
                .assertComplete()
                .assertValueCount(10)
    }
}