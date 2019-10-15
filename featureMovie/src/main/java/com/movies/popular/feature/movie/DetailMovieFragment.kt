package com.movies.popular.feature.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.movies.popular.common.BaseFragment
import com.movies.popular.common.helper.UiHelper
import com.movies.popular.feature.movie.databinding.FragmentMovieDetailBinding
import javax.inject.Inject

class DetailMovieFragment : BaseFragment() {

    companion object {

        private const val EXTRA_ID = "movieId"

        fun newInstance(movieId: Int): DetailMovieFragment {
            return DetailMovieFragment().apply {
                arguments = Bundle().apply { putInt(EXTRA_ID, movieId) }
            }
        }
    }

    private val component = MovieComponent.getInstance()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val movieId: Int by lazy { arguments!!.getInt(EXTRA_ID) }
    private val uiHelper: UiHelper by lazy { UiHelper(activity!!) }

    private lateinit var binding: FragmentMovieDetailBinding
    private lateinit var viewModel: DetailMovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(DetailMovieViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_movie_detail, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.movieId.value = movieId

        viewModel.uploading.observe(this, Observer {
            uiHelper.showUploading(it!!)
        })
        viewModel.errorMessage.observe(this, Observer {
            uiHelper.showErrorToast(it!!)
        })
    }
}
