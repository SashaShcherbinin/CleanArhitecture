package com.movies.popular.data.common

import com.movies.popular.data.common.cashe.CachePolicy
import io.reactivex.Completable
import io.reactivex.Single

@Suppress("unused")
class MemoryListStorage<Query, Entity>(max: Int, cachePolicy: CachePolicy,
                                       fetcher: ((Query) -> Single<List<Entity>>)?)
    : MemoryStorage<Query, List<Entity>>(max, cachePolicy, fetcher) {

    fun update(filter: (Entity) -> Boolean,
               onUpdateCallback: (Entity) -> Entity)
            : Completable {
        return cache.get()
                .concatMapCompletable { cacheEntity ->
                    Completable.fromAction {
                        val dataList = cacheEntity.value.entry.toMutableList()
                        var changed = false
                        for (index in 0 until dataList.size) {
                            val entity = dataList[index]
                            if (filter.invoke(entity)) {
                                val newEntity = onUpdateCallback.invoke(entity)
                                dataList.removeAt(index)
                                dataList.add(index, newEntity)
                                changed = true
                            }
                        }
                        if (changed) {
                            cache.put(cacheEntity.key, CachePolicy.createEntry(dataList))
                            updateSubject.onNext(cacheEntity.key)
                        }
                    }
                }
    }

    class Builder<Query, Entity> : MemoryStorage.Builder<Query, List<Entity>>()

}
