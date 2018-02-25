package com.infinite.rx

import com.infinite.rx.challenge.print
import com.infinite.rx.challenge.week3.materializeSource
import org.junit.Test

class MaterializeTest {

    @Test
    fun materializedStream() {

        val winners = arrayListOf<Int>()
        val stream = materializeSource
                .filter {
                    if(it.isOnError) return@filter false
                    if (winners.size == 2) return@filter false
                    if(!it.isOnNext) return@filter false
                    it.value?.let {
                        winners.add(it)
                        return@filter false
                    }
                    return@filter true
                }
                .dematerialize<Int>()
                .doOnNext { "emitted $it".print() }
                .test()

        stream.assertValueCount(2)
                .assertValues(1, 2)
                .assertComplete()
    }
}