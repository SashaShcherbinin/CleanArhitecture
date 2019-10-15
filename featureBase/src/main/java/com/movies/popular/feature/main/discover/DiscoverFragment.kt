package com.movies.popular.feature.main.discover

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
import com.movies.popular.common.adapter.LoadingAdapter
import com.movies.popular.common.helper.UiHelper
import com.movies.popular.domain.IntentGenerator
import com.movies.popular.feature.BaseComponent
import com.movies.popular.feature.R
import com.movies.popular.feature.databinding.FragmentMoviesBinding
import javax.inject.Inject

class DiscoverFragment : BaseFragment() {

    companion object {
        fun newInstance(): DiscoverFragment = DiscoverFragment()
    }

    private val component = BaseComponent.getInstance()

    @Inject
    lateinit var intentGenerator: IntentGenerator
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val moviesDelegate: DiscoverDelegate by lazy { activity as DiscoverDelegate }
    private val uiHelper: UiHelper by lazy { UiHelper(activity!!) }

    private lateinit var binding: FragmentMoviesBinding
    private lateinit var viewModel: DiscoverViewModel
    private val adapter: DiscoverAdapter by lazy { DiscoverAdapter(viewModel) }
    private val loadingAdapter: LoadingAdapter by lazy {
        LoadingAdapter(adapter, loadMoreListener = { viewModel.fetchNext() })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(DiscoverViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_movies, container, false)
        binding.recyclerView.layoutManager = GridLayoutManager(context, 3)
        binding.recyclerView.adapter = loadingAdapter
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.inflateMenu(R.menu.search)
        binding.toolbar.menu.findItem(R.id.searchItem).setOnMenuItemClickListener {
            startActivity(intentGenerator.getSearchActivityIntent())
            true
        }
        initObservers()
    }

    private fun initObservers() {
        viewModel.errorMessage.observe(this, Observer { uiHelper.showErrorToast(it) })
        viewModel.content.observe(this, Observer { adapter.submitList(it!!) })
        viewModel.itemClickedEvent.observe(this, Observer {
            moviesDelegate.onShowMovie(it)
        })
        viewModel.hasNext.observe(this, Observer { loadingAdapter.setHasNext(it!!) })
        viewModel.loading.observe(this, Observer { loadingAdapter.setLoading(it!!) })
    }
}