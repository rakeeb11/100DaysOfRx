package com.infinite.rx.challenge.week4

import io.reactivex.Observable
import java.util.*


val boostedNewsList = arrayListOf(
        BoostedNews(1),
        BoostedNews(2),
        BoostedNews(4),
        BoostedNews(3),
        BoostedNews(2),
        BoostedNews(2, true),
        BoostedNews(2, true),
        BoostedNews(3),
        BoostedNews(1),
        BoostedNews(2),
        BoostedNews(4),
        BoostedNews(4, true),
        BoostedNews(5),
        BoostedNews(5)
)

val distinctNewsUntilChangedStream = Observable.fromIterable(boostedNewsList)
        .distinctUntilChanged { news -> if(news.isPromoted) Random().nextInt()  else news.category }


data class BoostedNews(val category: Int, val isPromoted: Boolean = false)