package com.infinite.rx.challenge.week2

import android.widget.EditText
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import java.util.*
import java.util.concurrent.TimeUnit

val itemStream = Observable.fromIterable(listOf("a", "b", "c", "d", "e", "f"))

val switchMapStream = itemStream
        .switchMap { alphabet ->
            val delay = Random().nextInt(10)
            Observable.just("${alphabet}x")
                    .delay(delay.toLong(), TimeUnit.SECONDS)
        }
        .doOnNext { print(it) }

fun searchQueryStream(query: EditText) = RxTextView.textChanges(query)
fun searchResultStream(query: EditText) = searchQueryStream(query)
        .switchMap {
            val delay = Random().nextInt(10)
            Observable.just("${it}x")
                    .delay(delay.toLong(), TimeUnit.SECONDS)
        }
        .doOnNext { println(it) }
        .doOnError { it.printStackTrace() }
