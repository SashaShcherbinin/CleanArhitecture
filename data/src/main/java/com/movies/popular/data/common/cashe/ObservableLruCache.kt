package com.movies.popular.data.common.cashe

import android.util.LruCache
import io.reactivex.Maybe
import io.reactivex.Observable
import java.lang.ref.WeakReference
import java.util.*

class ObservableLruCache<K, V>(maxSize: Int) {

    companion object {

        private val savedCache = LinkedList<WeakReference<ObservableLruCache<*, *>>>()

        fun clean() {
            for (reference in savedCache) {
                val observableLruCache = reference.get()
                observableLruCache?.clear()
            }
        }
    }

    private val cache: LruCache<K, V> = LruCache(maxSize)

    val keys: Set<K> get() = cache.snapshot().keys

    init {
        saveCache()
    }

    private fun saveCache() {
        for (i in savedCache.indices.reversed()) {
            val reference = savedCache[i]
            if (reference.get() == null) {
                savedCache.remove(reference)
            }
        }
        savedCache.add(WeakReference(this))
    }

    operator fun get(key: K): Maybe<V> {
        val objectObservable = Maybe.create<V> { emitter ->
            val v = cache.get(key)
            if (v != null && !emitter.isDisposed) {
                emitter.onSuccess(v)
            }
            if (!emitter.isDisposed) {
                emitter.onComplete()
            }
        }
        return objectObservable
                .doOnSuccess { entity -> cache.put(key, entity) }
    }

    fun get(): Observable<CacheEntity> {
        return Observable.create { emitter ->
            val keySet = HashSet(cache.snapshot().keys)
            for (key in keySet) {
                if (emitter.isDisposed) {
                    break
                }
                val v = cache.get(key)
                if (v != null) {
                    emitter.onNext(CacheEntity(key, v))
                }

            }
            if (!emitter.isDisposed) {
                emitter.onComplete()
            }
        }
    }

    fun put(key: K, value: V) {
        cache.put(key, value)
    }

    fun remove(key: K) {
        cache.remove(key)
    }

    fun clear(key: K) {
        cache.remove(key)
    }

    fun clear() {
        cache.evictAll()
    }

    inner class CacheEntity internal constructor(var key: K, var value: V)

}
