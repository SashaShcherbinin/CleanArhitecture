package com.movies.popular.domain

import android.content.Context
import android.content.Intent

interface IntentGenerator {

    fun getSearchActivityIntent() : Intent

    fun getMovieDetailActivityIntent(id: Int) : Intent
}