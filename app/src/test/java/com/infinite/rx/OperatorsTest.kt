package com.infinite.rx

import android.widget.EditText
import android.widget.TextView
import com.infinite.rx.challenge.print
import com.infinite.rx.challenge.week1.simpleMapping
import com.infinite.rx.challenge.week2.*
import com.infinite.rx.challenge.week3.*
import com.infinite.rx.challenge.week4.*
import io.reactivex.Observable
import io.reactivex.ObservableSource
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

    @Test
    fun concatDelayError() {
        val stream = fusedStream.test()
        stream.assertNotComplete()
                .assertValueCount(3)
                .assertValueAt(0, "1 & Quick")
                .assertValueAt(1, "2 & Quick")
                .assertValueAt(2, "2 & Sort")
                .assertValues("1 & Quick", "2 & Quick", "2 & Sort")
                .assertErrorMessage("Demo")
    }

    @Test
    fun concat() {
        val stream = concatStream.test()
        stream.awaitTerminalEvent()
        stream.assertComplete()
                .assertValueAt(0, 2)
                .assertValueAt(1, 1)
                .assertValueAt(2, 3)
    }

    @Test
    fun merge() {
        val stream = mergeStream.test()
        stream.awaitTerminalEvent()
        stream.assertComplete()
                .assertValueAt(0, 2)
                .assertValueAt(4, 1)
                .assertValueAt(5, 3)
    }

    @Test
    fun mergedDelay() {
        val stream = mergedStream
                .test()
        stream.awaitTerminalEvent()
        stream.assertNotComplete()
    }

    @Test
    fun zippedStream() {
        val stream = studentStream.test()
        stream.assertComplete()
    }


    @Test
    fun zippedWithTimeStamp() {
        val stream = streamWithZipAndTimeStamp
                .doOnEvent { t1, t2 ->
                    "on event: 1: $t1".print()
                    "on event: 2: $t2".print()
                }
                .doOnSuccess {
                    "success $it".print()
                }
                .test()
        stream.awaitTerminalEvent()
        stream.assertComplete()
    }

    @Test
    fun combineLatestStreams() {
        val stream = latestStreams
                .doOnNext { "next: $it".print() }
                .test()
        stream.awaitTerminalEvent()
        stream.assertComplete()
    }

    @Test
    fun combineLatestUi() {
        val context = RuntimeEnvironment.application
        val email = TextView(context)
        val password = TextView(context)
        val stream = validInputStream(email, password)
                .test()
        email.text = "rakeeb@"
        password.text = "rakeeb"
        email.text = "rakeeb@evolveasia.co"
        password.text = "rakeeb00"
        stream.awaitTerminalEvent()
    }

    @Test
    fun ambiguous() {
        val stream = Observable.amb(arrayListOf<ObservableSource<String>>(
                stream(100, 17, "first"),
                stream(100, 10, "second")
        ))
                .take(5)
                .test()

        stream.awaitTerminalEvent()
        stream
                .assertValueCount(5)
                .assertComplete()
    }

    @Test
    fun scanning() {
        val stream = scanningSequence
                .test()
        stream.awaitTerminalEvent()
        stream
                .assertValueCount(4)
                .assertComplete()
    }

    @Test
    fun collectionBigNumbers() {
        val stream = collectionBigNumbersStream.test()
        stream.assertComplete()
                .assertValueCount(1)
                .assertValue(BigNumbers(28 ,29))
    }

    @Test
    fun groupBy() {
        val stream = groupedStream.test()
        stream.assertComplete()
    }

    @Test
    fun defaultIfEmpty() {
        val stream = emptyStream.test()
        stream.assertComplete()
                .assertValueCount(1)
                .assertValue(10)
    }

    @Test
    fun streamWithDefaultValue() {
        val stream = streamWithDefaultValue.test()
        stream.assertComplete()
                .assertValueCount(5)
                .assertValueAt(4, 5)
    }

    @Test
    fun streamWithDefaultValueAndFilter() {
        val stream = streamWithFilterAndDefaultValue.test()
        stream.assertComplete()
                .assertValueCount(1)
                .assertValue(11)
    }

    @Test
    fun distinctNews() {
        val stream = distinctNewsStream.test()
        stream.assertComplete()
                .assertValueCount(5)
    }

    @Test
    fun distinctUntilChangedNews() {
        val stream = distinctNewsUntilChangedStream.test()
        stream.assertComplete()
                .assertValueCount(12)
    }

    @Test
    fun simpleTake() {
        val stream = simpleTake.test()
        stream.assertComplete()
                .assertValueCount(NEWS_TAKE_COUNT)
    }

    @Test
    fun takeNewsStreamWhile() {
        val stream = takeNewsStreamWhile.test()
        stream
                .assertComplete()
                .assertValueCount(2)
                .assertValueAt(0, News(1))
                .assertValueAt(1, News(2))
                .assertNever(News(PROMOTED_NEWS_CATEGORY))
    }

    @Test
    fun takeLastTwoNews() {
        val stream = takeLastTwoNewsStream.test()
        stream
                .assertComplete()
                .assertValueCount(NEWS_TAKE_COUNT)
    }
}
