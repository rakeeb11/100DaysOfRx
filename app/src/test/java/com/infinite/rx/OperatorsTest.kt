package com.infinite.rx

import android.widget.EditText
import com.infinite.rx.challenge.week1.simpleMapping
import com.infinite.rx.challenge.week2.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.BlockJUnit4ClassRunner
import org.robolectric.RuntimeEnvironment
import java.util.concurrent.TimeUnit

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

    @Test
    fun switchMapping() {
        val switcher = switchMapStream.test()
        switcher.awaitTerminalEvent()
        switcher
                .assertValue("fx")
                .assertValueCount(1)
                .assertComplete()
    }

    @Test
    fun searchText() {
        val context = RuntimeEnvironment.application
        val userQuery = EditText(context)
        userQuery.setText("a")
        userQuery.setText("b")
        userQuery.setText("c")
        userQuery.setText("d")
        userQuery.setText("e")
        userQuery.setText("f")
        val stream = searchResultStream(userQuery)
                .delay(100, TimeUnit.MILLISECONDS)
                .test()

        stream.awaitTerminalEvent()
        stream
                .assertComplete()
    }
}