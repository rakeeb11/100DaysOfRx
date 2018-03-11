package com.infinite.rx.challenge.week4

import com.infinite.rx.challenge.print
import io.reactivex.Observable
import io.reactivex.observables.GroupedObservable

val groupedStream = Observable.range(10, 20)
        .groupBy {
            if (it % 2 == 0) return@groupBy "EVEN"
            return@groupBy "ODD"
        }
        .flatMapSingle {
            it.collect({ Pair(it.key, arrayListOf()) }, { collection: Pair<String?, ArrayList<Int>>, item: Int ->
                collection.second.add(item)
            })
        }
        .doOnNext { pair ->
            "call to doOnNext".print()
            val (identifiers: String?, groupedNumbers: ArrayList<Int>) = pair
            groupedNumbers.forEach {
                "key: $identifiers, value: $it".print()
            }
        }

val newsStream: Observable<GroupedNews> = Observable.range(10, 10)
        .map {
            News(when (it % 2 == 0) {
                true -> 2
                else -> 1
            })
        }
        .groupBy { it.category }


val categoryStream = newsStream
        .flatMapSingle {
            it.collect({ Pair(it.key, arrayListOf()) }, {
                newsPair: NewsCategoryPair, item: News ->
                newsPair.second.add(item)
            })
        }


typealias GroupedNews = GroupedObservable<Int, News>
typealias NewsCategoryPair = Pair<Int?, ArrayList<News>>

data class News(val category: Int)
