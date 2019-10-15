package com.movies.popular.data.common

import com.movies.popular.data.common.cashe.CachePolicy
import com.movies.popular.data.common.cashe.CachedEntry
import com.movies.popular.data.common.cashe.ObservableLruCache
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit

@Suppress("unused", "MemberVisibilityCanBePrivate")
open class MemoryStorage<Query, Entity>(max: Int,
                                        private val cachePolicy: CachePolicy,
                                        private val fetcher: ((Query) -> Single<Entity>)?) {

    protected val cache: ObservableLruCache<Query, CachedEntry<Entity>> = ObservableLruCache(max)

    protected val updateSubject = PublishSubject.create<Query>()

    private val fetchMap = ConcurrentHashMap<Query, Observable<Entity>>()

    operator fun get(query: Query): Observable<Entity> {
        val objectObservable = cache[query]
                .filter { cachePolicy.test(it) }
                .map<Entity> { it.entry }
                .toObservable()
        return objectObservable.repeatWhen { updateSubject.filter { q -> query == q } }
                .mergeWith(fetchIfExpired(query))
    }

    fun remove(query: Query): Completable {
        return Completable.fromAction {
            cache.remove(query)
            updateSubject.onNext(query)
        }
    }

    fun put(query: Query, entity: Entity): Completable {
        return Completable.fromAction {
            cache.put(query, CachePolicy.createEntry(entity))
            updateSubject.onNext(query)
        }
    }

    fun update(query: Query,
               filter: ((Entity) -> Boolean)? = null,
               onUpdateCallback: (Entity) -> Entity): Completable {
        return cache[query]
                .map<Entity> { it.entry }
                .let {
                    if (filter != null) it.filter(filter)
                    else it
                }
                .doOnSuccess { entity ->
                    val newEntity = onUpdateCallback.invoke(entity)
                    cache.put(query, CachePolicy.createEntry(newEntity))
                    updateSubject.onNext(query)
                }
                .ignoreElement()
    }

    fun refresh(query: Query): Completable {
        if (fetcher != null) {
            var observable: Observable<Entity>? = fetchMap[query]
            if (observable == null) {
                observable = fetcher.invoke(query)
                        .toObservable()
                        .doOnNext { entity ->
                            cache.put(query, CachePolicy.createEntry(entity))
                            updateSubject.onNext(query)
                        }
                        .doOnTerminate { fetchMap.remove(query) }
                        .doOnDispose { fetchMap.remove(query) }
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
        } else {
            return Completable.complete()
        }
    }

    private fun fetchIfExpired(query: Query): Observable<Entity> {
        return cache[query]
                .map { cachedEntry -> !cachePolicy.test(cachedEntry) }
                .toSingle(true)
                .filter { expired -> expired }
                .observeOn(Schedulers.io())
                .flatMapCompletable {
                    Completable.create { emitter ->
                        val exception = refresh(query).blockingGet()
                        if (exception != null && !emitter.isDisposed) {
                            emitter.onError(exception)
                        }
                        emitter.onComplete()
                    }
                }.toObservable()
    }

    open class Builder<Query, Entity> {

        private var max: Int = 0
        private var fetcher: ((Query) -> Single<Entity>)? = null
        private var cachePolicy: CachePolicy? = null

        fun capacity(max: Int): Builder<Query, Entity> {
            this.max = max
            return this
        }

        fun fetcher(fetcher: (Query) -> Single<Entity>): Builder<Query, Entity> {
            this.fetcher = fetcher
            return this
        }

        fun cachePolicy(cachePolicy: CachePolicy): Builder<Query, Entity> {
            this.cachePolicy = cachePolicy
            return this
        }

        fun build(): MemoryStorage<Query, Entity> {
            this.max = if (this.max == 0) 10 else this.max
            this.cachePolicy = this.cachePolicy ?: CachePolicy.create(5, TimeUnit.MINUTES)
            if (fetcher == null) {
                this.cachePolicy = CachePolicy.infinite()
            }
            return MemoryStorage(max, cachePolicy!!, fetcher)
        }
    }

}
