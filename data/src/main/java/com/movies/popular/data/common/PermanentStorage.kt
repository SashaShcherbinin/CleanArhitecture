package com.movies.popular.data.common

import com.movies.popular.data.common.cashe.CacheInfo
import com.movies.popular.data.common.cashe.CachePolicy
import com.movies.popular.data.common.cashe.ObservableLruCache
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.util.*
import java.util.concurrent.TimeUnit

@Suppress("MemberVisibilityCanBePrivate", "unused")
class PermanentStorage<Query, Entity>
private constructor(max: Int,
                    private val cachePolicy: CachePolicy,
                    private val fetcher: ((Query) -> Single<Entity>),
                    private val permanent: Permanent<Query, Entity>) {

    private val cacheInfo: ObservableLruCache<Query, CacheInfo> = ObservableLruCache(max)
    private val fetchMap = HashMap<Query, Observable<Entity>>()

    operator fun get(query: Query): Observable<Entity> {
        return permanent.read(query)
                .mergeWith(fetchIfExpired(query))
    }

    fun put(query: Query, entity: Entity): Completable {
        return permanent.write(query, entity)
    }

    fun refresh(query: Query): Completable {
        var observable: Observable<Entity>? = fetchMap[query]
        if (observable == null) {
            observable = fetcher.invoke(query)
                    .subscribeOn(Schedulers.io())
                    .toObservable()
                    .doOnNext { cacheInfo.put(query, CachePolicy.createEntry()) }
                    .flatMapCompletable { permanent.write(query, it) }
                    .doOnTerminate { fetchMap.remove(query) }
                    .doOnDispose { fetchMap.remove(query) }
                    .toObservable<Entity>()
                    .publish()
                    .refCount()
        }
        val finalObservable = observable
        return observable
                .doOnSubscribe {
                    if (!fetchMap.contains(query)) {
                        fetchMap[query] = finalObservable
                    }
                }
                .ignoreElements()
    }

    private fun fetchIfExpired(query: Query): Observable<Entity> {
        return cacheInfo[query]
                .map { cachedEntry -> !cachePolicy.test(cachedEntry) }
                .toSingle(true)
                .filter { expired -> expired }
                .flatMapCompletable { refresh(query) }
                .toObservable()
    }

    fun <T> makeAction(query: Query, observable: Observable<T>): Observable<T> {
        return fetchIfExpired(query).ignoreElements().andThen(observable)
    }

    class Builder<Query, Entity>(
            private val fetcher: ((Query) -> Single<Entity>),
            private val permanent: Permanent<Query, Entity>
    ) {

        private var max: Int = 0
        private var cachePolicy: CachePolicy? = null

        fun capacity(max: Int): Builder<Query, Entity> {
            this.max = max
            return this
        }

        fun cachePolicy(cachePolicy: CachePolicy): Builder<Query, Entity> {
            this.cachePolicy = cachePolicy
            return this
        }

        fun build(): PermanentStorage<Query, Entity> {
            this.max = if (this.max == 0) 10 else this.max
            this.cachePolicy =
                    if (this.cachePolicy != null) this.cachePolicy
                    else CachePolicy.create(5, TimeUnit.MINUTES)
            return PermanentStorage(
                    max = max,
                    cachePolicy = cachePolicy!!,
                    fetcher = fetcher,
                    permanent = permanent
            )
        }
    }

    abstract class Permanent<Query, Entity> {
        abstract fun read(query: Query): Observable<Entity>

        abstract fun write(query: Query, entity: Entity): Completable
    }
}
