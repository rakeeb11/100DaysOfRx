package com.infinite.rx.challenge.week3

import android.content.Context
import android.widget.Button
import android.widget.TextView
import com.infinite.rx.challenge.print
import com.infinite.rx.challenge.week2.User
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import java.util.concurrent.TimeUnit

// we assume that item(s) will produce events at the same frequency
// and at similar point in time
// overtime the zip operator can lead to stale data or memory leaks

val latestStreams = Observable.combineLatest(
        Observable.interval(17, TimeUnit.MILLISECONDS).map { x -> "S$x" },
        Observable.interval(10, TimeUnit.MILLISECONDS).map { x -> "F$x" },
        BiFunction { first: String, second: String -> "$first:$second" }
).take(20)!!


fun emailStream(textView: TextView) = RxTextView.textChanges(textView)
        .map { it.length > 8 }
        .distinctUntilChanged()!!

fun passwordStream(textView: TextView) = RxTextView.textChanges(textView)
        .map { it.length > 8 }
        .distinctUntilChanged()!!

fun validInputStream(textUserName: TextView, textPassword: TextView) = Observable
        .combineLatest(emailStream(textUserName), passwordStream(textPassword),
                BiFunction { isUserNameValid: Boolean, isPasswordValid: Boolean ->
                    isUserNameValid && isPasswordValid
                })
        .doOnNext { "valid state: $it".print() }!!


