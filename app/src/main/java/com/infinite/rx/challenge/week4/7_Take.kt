package com.infinite.rx.challenge.week4

import io.reactivex.Observable

const val NEWS_TAKE_COUNT = 3

// simple take
val simpleTake = Observable.fromIterable(newsList)
        .take(NEWS_TAKE_COUNT.toLong())

// take until
val takeNewsStreamWhile = Observable.fromIterable(newsList)
        // break news stream after the promoted news category was found
        .takeWhile { news -> news.category != PROMOTED_NEWS_CATEGORY }

// take last implementation
val takeLastTwoNewsStream = Observable.fromIterable(newsList)
        .takeLast(NEWS_TAKE_COUNT)

