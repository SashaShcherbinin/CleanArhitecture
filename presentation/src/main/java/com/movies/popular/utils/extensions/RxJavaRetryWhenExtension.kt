package com.movies.popular.utils.extensions

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

/**
 * @param delay the initial delay before emitting, default value 5
 * @param timeUnit time units to use for delay, default TimeUnit.SECONDS
 */
fun <T> Observable<T>.defaultRetryWhen(
        delay: Long = 5,
        timeUnit: TimeUnit = TimeUnit.SECONDS
): Observable<T> {
    return this.retryWhen { observable ->
        observable.flatMap {
            Observable.timer(delay, timeUnit,
                    AndroidSchedulers.mainThread())
        }
    }
}

/**
 * @param delay the initial delay before emitting, default value 5
 * @param timeUnit time units to use for delay, default TimeUnit.SECONDS
 */
fun <T> Single<T>.defaultRetryWhen(
        delay: Long = 5,
        timeUnit: TimeUnit = TimeUnit.SECONDS
): Single<T> {
    return this.retryWhen { single ->
        single.flatMap {
            Flowable.timer(delay, timeUnit,
                    AndroidSchedulers.mainThread())
        }
    }
}

/**
 * @param delay the initial delay before emitting, default value 5
 * @param timeUnit time units to use for delay, default TimeUnit.SECONDS
 */
fun Completable.defaultRetryWhen(
        delay: Long = 5,
        timeUnit: TimeUnit = TimeUnit.SECONDS
): Completable {
    return this.retryWhen { completable ->
        completable.flatMap {
            Flowable.timer(delay, timeUnit,
                    AndroidSchedulers.mainThread())

        }
    }
}