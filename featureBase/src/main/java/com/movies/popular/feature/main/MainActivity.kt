package com.movies.popular.feature.main

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.movies.popular.common.BaseActivity
import com.movies.popular.domain.FragmentGenerator
import com.movies.popular.feature.BaseComponent
import com.movies.popular.feature.R
import com.movies.popular.feature.databinding.ActivityMainBinding
import com.movies.popular.feature.filter.FilterActivity
import com.movies.popular.feature.main.discover.DiscoverDelegate
import com.movies.popular.feature.main.discover.DiscoverFragment
import javax.inject.Inject

class MainActivity : BaseActivity(), DiscoverDelegate {

    private val component = BaseComponent.getInstance()

    private lateinit var binding: ActivityMainBinding
    @Inject
    lateinit var fragmentGenerator: FragmentGenerator

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .add(R.id.containerVg, DiscoverFragment.newInstance())
                    .commit()
        }
    }

    fun openFilterActivity(v: View) {
        startActivity(FilterActivity.getIntent(this))
    }

    override fun onShowMovie(id: Int) {
        supportFragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.containerVg, fragmentGenerator.getMovieFragment(id))
                .commit()
    }
}
