package com.infinite.rx.challenge.week1

import android.support.annotation.VisibleForTesting
import io.reactivex.Observable

// building high level and easy to reason data pipelines
// RxJava has a lot of built in operators
// operator takes an upstream Observable<T> and converts it to
// a downstream Observable<R>

// you either extract, enrich or wrap the original events

// chaining several operators, forking stream into multiple substreams
// and joining them back is idiomatic in Rx


// operators are typically instance methods on Observable that alert
// behavior of upstream Observable(s) as seen by downstream Observable(s)

// NEW: ongoing research of operator fusion which aims to seamlessly collapse
// several operators into one so that stack depth for chaining events is not as substantial

// Every single operator returns a new Observable; leaving the upstream Observable(s) untouched

// this approach allows us to fork a stream into multiple independent sources

@VisibleForTesting
fun simpleMapping(): Observable<String> {
    return Observable.just(8, 9, 10)
            // is multiple of 3?
            .filter { it % 3 > 0 }
            // multiply 3's multiple by 10
            .map { "# ${it * 10}" }
            .filter { it.length < 4 }
}