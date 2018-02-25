package com.infinite.rx.challenge.week3

import android.os.Build
import android.support.annotation.RequiresApi
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Timed
import java.time.LocalDate
import java.util.concurrent.TimeUnit

val rollNoStream = Observable.range(1, 5)!!
val nameStream = Observable.fromIterable(listOf("Rakeeb"
        , "Nirvana"
        , "Rahil"
        , "Anjan"
        , "Prawesh"))!!

val studentStream = Observable.zip(rollNoStream, nameStream,
        BiFunction { rollNo: Int, name: String ->
            Student(name, rollNo)
        })

data class Student(val name: String, val id: Int)


val red = Observable.interval(10, TimeUnit.MILLISECONDS)!!
val green = Observable.interval(10, TimeUnit.MILLISECONDS)!!

val streamWithZipAndTimeStamp = Observable.zip(red.timestamp(),
        green.timestamp(),
        BiFunction { red: Timed<Long>, green: Timed<Long> -> red.time() - green.time() })
        .firstOrError()!!
