package com.movies.popular.utils

import java.util.HashMap

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

object RxDisposable {

    private val sSubscriptions = HashMap<Any, SpecificCompositeDisposable>()

    fun manage(tag: Any, subscription: Disposable) {
        var subscriptions: SpecificCompositeDisposable? = sSubscriptions[tag]
        if (subscriptions == null) {
            subscriptions = SpecificCompositeDisposable()
            sSubscriptions[tag] = subscriptions
        }

        subscriptions.add(subscription)
    }

    fun manage(tag: Any, subscriptionTab: Any, subscription: Disposable?) {
        var subscriptions: SpecificCompositeDisposable? = sSubscriptions[tag]
        if (subscriptions == null) {
            subscriptions = SpecificCompositeDisposable()
            sSubscriptions[tag] = subscriptions
        }

        subscriptions.add(subscriptionTab, subscription)
    }

    fun unsubscribe(tag: Any) {
        val subscriptions = sSubscriptions[tag]
        if (subscriptions != null) {
            subscriptions.dispose()
            sSubscriptions.remove(tag)
        }
    }

    private class SpecificCompositeDisposable {

        internal val mCompositeDisposable = CompositeDisposable()
        internal val mDisposableHashMap = HashMap<Any, Disposable>()

        internal fun dispose() {
            mCompositeDisposable.dispose()
        }

        internal fun add(disposable: Disposable): Boolean {
            return mCompositeDisposable.add(disposable)
        }

        internal fun add(subscriptionTab: Any, disposable: Disposable?): Boolean {
            if (disposable == null) {
                return false
            }
            val oldDisposable = mDisposableHashMap[subscriptionTab]
            if (oldDisposable != null) {
                mCompositeDisposable.remove(oldDisposable)
            }
            mDisposableHashMap[subscriptionTab] = disposable
            return mCompositeDisposable.add(disposable)
        }
    }

}
