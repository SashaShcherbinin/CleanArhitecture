package com.movies.popular.common.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class RecyclerViewAdapterWrapper(wrapped: RecyclerView.Adapter<out RecyclerView.ViewHolder>)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    protected var wrappedAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>
    private val innerObserver: RecyclerView.AdapterDataObserver by lazy {
        getAdapterDataObserver()
    }
    private var registeredObserver = false

    init {
        @Suppress("UNCHECKED_CAST")
        this.wrappedAdapter = wrapped as RecyclerView.Adapter<RecyclerView.ViewHolder>
    }

    abstract fun getAdapterDataObserver(): RecyclerView.AdapterDataObserver

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return wrappedAdapter.onCreateViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        wrappedAdapter.onBindViewHolder(holder, position)
    }

    override fun getItemViewType(position: Int): Int {
        return wrappedAdapter.getItemViewType(position)
    }

    override fun setHasStableIds(hasStableIds: Boolean) {
        wrappedAdapter.setHasStableIds(hasStableIds)
    }

    override fun getItemId(position: Int): Long {
        return wrappedAdapter.getItemId(position)
    }

    override fun getItemCount(): Int {
        return wrappedAdapter.itemCount
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        wrappedAdapter.onViewRecycled(holder)
    }

    override fun onFailedToRecycleView(holder: RecyclerView.ViewHolder): Boolean {
        return true
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        wrappedAdapter.onViewAttachedToWindow(holder)
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        wrappedAdapter.onViewDetachedFromWindow(holder)
    }

    override fun registerAdapterDataObserver(observer: RecyclerView.AdapterDataObserver) {
        super.registerAdapterDataObserver(observer)
        if (!registeredObserver) {
            wrappedAdapter.registerAdapterDataObserver(innerObserver)
            registeredObserver = true
        }
    }

    override fun unregisterAdapterDataObserver(observer: RecyclerView.AdapterDataObserver) {
        super.unregisterAdapterDataObserver(observer)
        if (registeredObserver) {
            wrappedAdapter.unregisterAdapterDataObserver(innerObserver)
            registeredObserver = false
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        wrappedAdapter.onAttachedToRecyclerView(recyclerView)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        wrappedAdapter.onDetachedFromRecyclerView(recyclerView)
    }

}

