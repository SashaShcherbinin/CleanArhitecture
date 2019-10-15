package com.movies.popular.feature.movie

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.movies.popular.common.BaseActivity
import com.movies.popular.feature.movie.databinding.ActivityMovieDetailBinding

class MovieDetailActivity : BaseActivity() {

    companion object {
        private const val EXTRA_ID = "EXTRA_ID"

        fun getIntent(context: Context, id: Int): Intent =
                Intent(context, MovieDetailActivity::class.java)
                        .putExtra(EXTRA_ID, id)
    }

    private val component = MovieComponent.getInstance()
    private val extraId by lazy { intent.getIntExtra(EXTRA_ID, 0) }
    private lateinit var binding: ActivityMovieDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .add(R.id.containerVg, DetailMovieFragment.newInstance(extraId))
                    .commit()
        }
    }

}