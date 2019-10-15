package com.movies.popular.common.binding

import android.app.Activity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.BindingAdapter

object ToolbarBindingAdapter {

    @JvmStatic
    @BindingAdapter(value = ["android:bind_toolbar_go_back"], requireAll = false)
    fun formatHtmlText(toolbar: Toolbar, goBack: Boolean) {
        if (goBack) {
            toolbar.setNavigationOnClickListener { (toolbar.context as Activity).onBackPressed() }
        }
    }

}
