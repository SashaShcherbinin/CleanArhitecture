package com.movies.popular.common.extension

import androidx.lifecycle.MutableLiveData


/**
 * Set value only if previous was null or not set
 */
fun <T> MutableLiveData<T>.setValueIfEmpty(value: T?) {
    if (this.value == null) {
        setValueIgnoreNull(value)
    }
}

/**
 * Set value only if new is not null
 */
fun <T> MutableLiveData<T>.setValueIgnoreNull(value: T?) {
    value?.let { setValue(value) }
}