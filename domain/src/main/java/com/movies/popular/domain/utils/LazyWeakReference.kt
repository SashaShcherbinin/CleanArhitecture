package com.movies.popular.domain.utils

import java.lang.ref.WeakReference

class LazyWeakReference<T> constructor(private val creation: () -> T) {

    private var weakReference: WeakReference<T>? = null

    fun get(): T {
        return if (weakReference == null) {
            val referent = creation.invoke()
            weakReference = WeakReference(referent)
            referent
        } else {
            val get = weakReference!!.get()
            if (get == null) {
                val referent = creation.invoke()
                weakReference = WeakReference(referent)
                referent
            } else get
        }
    }
}
