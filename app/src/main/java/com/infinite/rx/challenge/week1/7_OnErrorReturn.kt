package com.infinite.rx.challenge.week1

import io.reactivex.Observable

val NO_GAME = Game()

val gameStream = Observable.create<Game> { emitter ->
    emitter.onError(Throwable("Demo"))
}
        .onErrorReturn { NO_GAME }!!


class Game(val home: String = "", val away: String = "")