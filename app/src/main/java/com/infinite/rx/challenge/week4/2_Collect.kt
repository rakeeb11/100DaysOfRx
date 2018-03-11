package com.infinite.rx.challenge.week4

import io.reactivex.Observable
import io.reactivex.functions.BiFunction

const val INITIAL_SCORE = 0
const val MULTIPLIER = 0

val INITIAL_VALUE: () -> ArrayList<Int> = { arrayListOf() }
val COLLECTOR: (ArrayList<Int>, Int) -> Unit = { list: ArrayList<Int>, item: Int -> list.add(item) }
val collectionStream = Observable.range(10, 20)
        .collect(INITIAL_VALUE, COLLECTOR)

val collectionBigNumbersStream = Observable.range(10, 20)
        .collect({ BigNumbers() }, { numbers: BigNumbers, item: Int  ->
            if(item % 2 == 0) {
                if(numbers.even < item) numbers.even = item
                return@collect
            }
            if(numbers.odd < item) numbers.odd = item
        })!!

data class BigNumbers(var even: Int = 0, var odd: Int = 1)

val reductionStream = Observable.range(10 , 20)
        .reduce(Pair(INITIAL_SCORE, MULTIPLIER), BiFunction { pair: Pair<Int, Int>, item: Int ->
            // calculate score
            val score = pair.first + (item * pair.second)
            // update multiplier
            val multiplier = updateMultiplier(pair.first)
            return@BiFunction Pair(score, multiplier)
        })

fun updateMultiplier(first: Int): Int {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
}

val simpleReductionStream = Observable.range(10, 20)
        .reduce { accumulator: Int, item: Int -> accumulator + item }

val simpleScanStream = Observable.range(10, 20)
        .scan { accumulated: Int, item: Int -> accumulated + item }