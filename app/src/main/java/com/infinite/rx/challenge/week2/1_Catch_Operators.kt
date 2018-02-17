package com.infinite.rx.challenge.week2

import io.reactivex.Observable

const val NORMAL_STREAM_MESSAGE = "From normal stream"
const val ERROR_STREAM_MESSAGE = "From error stream"


val numberStreamsWithErrorResume = Observable.create<String> { emitter ->
    emitter.onNext(NORMAL_STREAM_MESSAGE)
    emitter.onError(Exception())
}
        .onErrorResumeNext(Observable.just(ERROR_STREAM_MESSAGE))!!

val numberStreamsWithExceptionResume = Observable.create<String> { emitter ->
    emitter.onNext(NORMAL_STREAM_MESSAGE)
    emitter.onError(Throwable("DEMO"))
}
        .onExceptionResumeNext(Observable.just(ERROR_STREAM_MESSAGE))!!