package com.movies.popular.presentation.feature.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.movies.popular.common.BaseActivity
import com.movies.popular.presentation.feature.search.fragment.SearchFragment
import com.movies.popular.presentation.search.R
import com.movies.popular.presentation.search.databinding.ActivitySearchBinding

class SearchActivity : BaseActivity() {

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, SearchActivity::class.java)
        }
    }

    private lateinit var binding: ActivitySearchBinding

    private val component = SearchComponent.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .add(R.id.containerVg, SearchFragment.newInstance())
                    .commit()
        }
    }
}
