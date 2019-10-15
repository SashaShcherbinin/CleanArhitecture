package com.movies.popular.common.livedata

import android.os.CountDownTimer
import androidx.annotation.MainThread
import androidx.lifecycle.MutableLiveData

open class DebounceMutableLiveData<T>(millis: Long) : MutableLiveData<T>() {

    companion object {
        var ignoreTimer = false
    }

    private var newValue: T? = null

    protected var timer = object : CountDownTimer(millis, Long.MAX_VALUE) {
        override fun onTick(millisUntilFinished: Long) {
        }

        override fun onFinish() {
            setSuperValue(newValue)
        }
    }

    @MainThread
    private fun setSuperValue(t: T?) {
        super.setValue(t)
    }

    @MainThread
    override fun setValue(t: T?) {
        newValue = t
        if (ignoreTimer) {
            setSuperValue(newValue)
        } else {
            timer.cancel()
            timer.start()
        }
    }

}