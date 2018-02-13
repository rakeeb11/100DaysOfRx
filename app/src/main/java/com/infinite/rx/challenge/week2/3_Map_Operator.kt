package com.infinite.rx.challenge.week2

import io.reactivex.Observable

val userIdStream = Observable.range(1, 10)!!
val userStream = userIdStream.map { User(it) }!!

data class User(val id: Int)