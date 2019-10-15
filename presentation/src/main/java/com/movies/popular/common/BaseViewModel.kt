package com.movies.popular.common

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.movies.popular.common.livedata.SingleLiveEvent
import com.movies.popular.utils.RxDisposable

open class BaseViewModel : ViewModel() {

    var errorMessage = SingleLiveEvent<String>()
    var uploading = MutableLiveData<Boolean>()

    override fun onCleared() {
        super.onCleared()
        RxDisposable.unsubscribe(this)
    }
}
