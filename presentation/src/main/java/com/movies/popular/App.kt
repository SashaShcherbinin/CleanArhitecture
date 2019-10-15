package com.movies.popular

import androidx.multidex.MultiDexApplication
import com.movies.popular.di.PresentationComponent
import timber.log.Timber

abstract class App : MultiDexApplication() {

    companion object {
        private var appComponent: PresentationComponent? = null
        fun getComponent() = appComponent
    }

    abstract fun getInjector(): PresentationComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = getInjector()
        appComponent!!.inject(this)

        initTimber()
    }

    private fun initTimber() {
        Timber.plant(Timber.DebugTree())
    }

}
