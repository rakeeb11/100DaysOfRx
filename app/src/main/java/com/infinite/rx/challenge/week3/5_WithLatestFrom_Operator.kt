package com.infinite.rx.challenge.week3

import android.content.Context
import android.widget.Button
import android.widget.TextView
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.functions.BiFunction

fun tapStream(context: Context, button: Button) = RxView.clicks(button)
        .withLatestFrom(validInputStream(TextView(context), TextView(context)),
                BiFunction { click: Any, status: Boolean -> status })
