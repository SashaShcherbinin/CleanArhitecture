package com.movies.popular.app

import com.movies.popular.App
import com.movies.popular.app.generator.FragmentGeneratorImpl
import com.movies.popular.app.generator.IntentGeneratorImpl
import com.movies.popular.data.di.DataComponent
import com.movies.popular.di.PresentationComponent

class InjectorApplication : App() {

    private val presentationComponent: PresentationComponent by lazy {
        val dataComponent = DataComponent.Initializer
                .init(this)

        PresentationComponent.Initializer
                .init(
                        this,
                        IntentGeneratorImpl(this),
                        FragmentGeneratorImpl(),
                        dataComponent
                )
    }

    override fun getInjector(): PresentationComponent {
        return presentationComponent
    }

}