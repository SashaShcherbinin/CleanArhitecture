package com.movies.popular.common.helper

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.movies.popular.R

class UiHelper
constructor(private val activity: Activity) {

    private var errorToast: Toast? = null
    private var uploadingDialog: AlertDialog? = null

    fun showErrorToast(message: String?) {
        if (errorToast != null) {
            errorToast!!.cancel()
        }
        errorToast = Toast.makeText(activity, message, Toast.LENGTH_LONG)
        errorToast!!.show()
    }

    fun showUploading(show: Boolean) {
        if (show) showUploading() else hideUploading()
    }

    private fun showUploading() {
        if (uploadingDialog == null) {
            uploadingDialog = AlertDialog.Builder(activity)
                    .setView(R.layout.view_loading_dialog)
                    .show()
            uploadingDialog!!.setCancelable(false)
        } else {
            uploadingDialog!!.show()
        }
    }

    private fun hideUploading() {
        if (uploadingDialog != null) {
            uploadingDialog!!.dismiss()
        }
    }

    fun showMessage(message: String) {
        if (errorToast != null) {
            errorToast!!.cancel()
        }
        errorToast = Toast.makeText(activity, message, Toast.LENGTH_LONG)
        errorToast!!.show()
    }

    fun showKeyBoard(view: View) {
        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED)
    }

}
