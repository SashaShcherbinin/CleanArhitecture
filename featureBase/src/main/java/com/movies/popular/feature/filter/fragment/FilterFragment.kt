package com.movies.popular.feature.filter.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.movies.popular.common.BaseFragment
import com.movies.popular.feature.BaseComponent
import com.movies.popular.feature.R
import com.movies.popular.feature.databinding.FragmentFilterBinding
import javax.inject.Inject

class FilterFragment : BaseFragment() {

    companion object {
        fun newInstance(): FilterFragment = FilterFragment()
    }

    private val component = BaseComponent.getInstance()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: FragmentFilterBinding
    private lateinit var viewModel: FilterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(FilterViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_filter, container, false)
        binding.viewModel = viewModel

        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
    }

    private fun initObservers() {
        viewModel.array.observe(this, Observer {
            binding.spinner.adapter =
                    ArrayAdapter(context!!, android.R.layout.simple_spinner_item, it)
        })
    }
}