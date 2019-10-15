package com.movies.popular.common.binding

import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

object SwipeBindingAdapter {

    @JvmStatic
    @BindingAdapter(value = ["android:bind_srl_onRefresh"], requireAll = false)
    fun onRefresh(swipe: SwipeRefreshLayout, listener: SwipeRefreshLayout.OnRefreshListener) {
        swipe.setOnRefreshListener(listener)
    }

    @JvmStatic
    @BindingAdapter(value = ["android:bind_srl_is_refreshing"], requireAll = false)
    fun isRefresh(swipe: SwipeRefreshLayout, refreshing: Boolean) {
        swipe.isRefreshing = refreshing
    }

}
