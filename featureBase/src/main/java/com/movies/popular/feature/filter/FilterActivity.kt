package com.movies.popular.feature.filter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.movies.popular.common.BaseActivity
import com.movies.popular.common.BaseFragment
import com.movies.popular.feature.BaseComponent
import com.movies.popular.feature.R
import com.movies.popular.feature.databinding.ActivityFilterBinding
import com.movies.popular.feature.filter.fragment.FilterFragment

class FilterActivity : BaseActivity() {

    private lateinit var binding: ActivityFilterBinding

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, FilterActivity::class.java)
        }
    }

    private val component = BaseComponent.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_filter)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .add(R.id.containerVg, FilterFragment.newInstance())
                    .commit()
        }
    }
}
