package com.movies.popular.presentation.feature.search.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.movies.popular.common.BaseFragment
import com.movies.popular.common.helper.UiHelper
import com.movies.popular.domain.IntentGenerator
import com.movies.popular.presentation.feature.search.SearchComponent
import com.movies.popular.presentation.search.R
import com.movies.popular.presentation.search.databinding.FragmentSearchBinding
import javax.inject.Inject

class SearchFragment : BaseFragment() {

    companion object {
        fun newInstance(): SearchFragment {
            return SearchFragment()
        }
    }

    private val component = SearchComponent.getInstance()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var intentGenerator: IntentGenerator

    private lateinit var binding: FragmentSearchBinding
    private lateinit var viewModel: SearchViewModel

    private val uiHelper: UiHelper by lazy { UiHelper(activity!!) }
    private val adapter: SearchAdapter by lazy { SearchAdapter(viewModel) }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(SearchViewModel::class.java)

    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_search, container, false)
        binding.recyclerView.layoutManager = GridLayoutManager(context, 3)
        binding.recyclerView.adapter = adapter

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
    }

    private fun initObservers() {
        viewModel.errorMessage.observe(this, Observer { uiHelper.showErrorToast(it) })
        viewModel.content.observe(this, Observer { adapter.submitList(it!!) })
        viewModel.itemClickedEvent.observe(this, Observer {
            startActivity(intentGenerator
                    .getMovieDetailActivityIntent(it))
        })

    }
}