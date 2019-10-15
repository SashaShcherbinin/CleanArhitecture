package com.movies.popular.common.livedata

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import timber.log.Timber
import java.util.concurrent.atomic.AtomicBoolean

class DebounceSingleLiveEvent<T> constructor(millisInFuture: Long, liveData: LiveData<*>)
    : DebounceMutableLiveData<T>(millisInFuture) {

    private val pending = AtomicBoolean(false)

    init {
        liveData.observeForever {
            timer.cancel()
        }
    }

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        if (hasActiveObservers()) {
            Timber.w("Multiple observers registeredObserver but only one will be notified of changes.")
        }

        // Observe the internal MutableLiveData
        super.observe(owner, Observer {
            if (pending.compareAndSet(true, false)) {
                observer.onChanged(it)
            }
        })
    }

    @MainThread
    override fun setValue(t: T?) {
        super.setValue(t)
        pending.set(true)
    }

    /**
     * Used for cases where T is Void, to make calls cleaner.
     */
    @MainThread
    fun call() {
        value = null
    }
}