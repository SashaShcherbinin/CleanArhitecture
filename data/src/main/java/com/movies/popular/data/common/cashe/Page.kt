package com.movies.popular.data.common.cashe

import java.util.*

class Page<T>(var hasNext: Boolean = false) {

    val dataList: MutableList<T> = Collections.synchronizedList(ArrayList())
    var page: Int = 1
        private set
    var error: Throwable? = null
    var maxCount: Int = 0

    fun getLastObject(): T? {
        return if (dataList.size > 0) dataList[dataList.size - 1] else null

    }

    fun replace(index: Int, entity: T) {
        synchronized(dataList) {
            dataList.removeAt(index)
            dataList.add(index, entity)
        }
    }

    fun addResult(result: List<T>) {
        dataList.addAll(result)
        page++
    }

    fun getPage(refresh: Boolean): Int {
        if (refresh) page = 1
        return page
    }

    fun clean() {
        dataList.clear()
        error = null
        page = 1
    }

    fun size(): Int {
        return dataList.size
    }

    fun remove(index: Int) {
        dataList.removeAt(index)
    }

    fun add(entity: T) {
        dataList.add(entity)
    }
}
