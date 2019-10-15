package com.movies.popular.common.binding

import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.Spinner
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener

object SpinnerBindingAdapter {

    @JvmStatic
    @BindingAdapter("android:bind_sp_position")
    fun setSpinnerPosition(view: Spinner, position: Int) {
        view.setSelection(position, true)
    }

    @JvmStatic
    @InverseBindingAdapter(attribute = "android:bind_sp_position")
    fun getSpinnerPosition(view: Spinner): Int {
        return view.selectedItemPosition
    }

    @BindingAdapter("android:bind_sp_positionAttrChanged")
    @JvmStatic
    fun setListenersSpinnerPosition(
            view: Spinner,
            attrChange: InverseBindingListener
    ) {
        view.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>,
                                        selectedItemView: View,
                                        position: Int, id: Long) {
                attrChange.onChange()
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {

            }
        }
    }
}