package com.infinite.rx.challenge.week4

import io.reactivex.Observable
import java.util.*

const val PROMOTED_NEWS_CATEGORY = 4

val newsList = arrayListOf(
        News(1),
        News(2),
        News(4),
        News(3),
        News(2),
        News(2),
        News(3),
        News(1),
        News(2),
        News(4),
        News(4),
        News(5)
)

val distinctNewsStream = Observable.fromIterable(newsList)
        .distinct()

val distinctWithKeySelector = Observable.fromIterable(newsList)
        // allow repeating for promoted category
        .distinct { news -> if(news.category == PROMOTED_NEWS_CATEGORY) Random().nextInt() else news.category }