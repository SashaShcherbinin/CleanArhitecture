package com.movies.popular.common.extension

import android.widget.EditText

/**
 * Created with Android Studio.
 * User: Danil Konovalenko
 * Date: 7/3/18
 * Time: 4:40 PM
 */
fun EditText.correctSelectionWithInsertedText() {
    if (this.selectionEnd == 0) {
        this.setSelection(this.length())
    }
}