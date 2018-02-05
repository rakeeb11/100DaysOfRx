package com.infinite.rx

import com.infinite.rx.challenge.week1.simpleMapping
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
}