package com.movies.popular.creator

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment

interface MovieScreenCreator {

    fun getMovieFragment(movieId: Int): Fragment
    fun getMovieIntent(context: Context, movieId: Int): Intent
}