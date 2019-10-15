package com.movies.popular.utils.extensions

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import java.io.Serializable

fun Bundle?.restoreInt(key: String, liveData: MutableLiveData<Int>, default: Int? = null) {
    val value = this?.getInt(key)
    if (value != null) {
        liveData.value = value
    } else if (default != null) {
        liveData.value = default
    }
}

fun Bundle?.restoreSerializable(key: String, liveData: MutableLiveData<out Serializable>) {
    this?.getSerializable(key)?.let {
        liveData.value = it
    }
}

fun Bundle.saveInt(key: String, liveData: MutableLiveData<Int>) {
    liveData.value?.let {
        this.putInt(key, it)
    }
}

fun Bundle.saveSerializable(key: String, liveData: MutableLiveData<out Serializable>) {
    liveData.value?.let {
        this.putSerializable(key, it)
    }
}