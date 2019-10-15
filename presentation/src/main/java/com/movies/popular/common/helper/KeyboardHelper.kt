package com.movies.popular.common.helper

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import javax.inject.Inject

class KeyboardHelper
@Inject
constructor(private val activity: Activity) {

    val isKeyboardVisible: Boolean
        get() {
            val imm = activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            return imm.isActive
        }

    fun hideSoftKeyboard(view: View) {
        val inputMethodManager = activity
                .getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
