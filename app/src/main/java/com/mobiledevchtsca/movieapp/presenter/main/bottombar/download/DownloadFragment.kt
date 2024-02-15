package com.mobiledevchtsca.movieapp.presenter.main.bottombar.download

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.ferfalk.simplesearchview.SimpleSearchView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mobiledevchtsca.movieapp.MainGraphDirections
import com.mobiledevchtsca.movieapp.R
import com.mobiledevchtsca.movieapp.databinding.BottomSheetDeleteMovieBinding
import com.mobiledevchtsca.movieapp.databinding.FragmentDownloadBinding
import com.mobiledevchtsca.movieapp.domain.model.Movie
import com.mobiledevchtsca.movieapp.presenter.main.bottombar.download.adapter.DownloadMovieAdapter
import com.mobiledevchtsca.movieapp.presenter.main.moviegenre.MovieGenreFragmentArgs
import com.mobiledevchtsca.movieapp.util.calculateFileSize
import com.mobiledevchtsca.movieapp.util.calculateMovieTime
import com.mobiledevchtsca.movieapp.util.hideKeyboard
import com.mobiledevchtsca.movieapp.util.initToolbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DownloadFragment : Fragment() {

    private var _binding: FragmentDownloadBinding? = null
    private val binding get() = _binding!!

    private val args: MovieGenreFragmentArgs by navArgs()
    private lateinit var mAdapter: DownloadMovieAdapter

    private val viewModel: DownloadViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDownloadBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(toolbar = binding.toolbar)

        initRecycler()

        initObservers()

        getData()

        initSearchView();
    }

    private fun getData() {
        viewModel.getMovies()
    }

    private fun initObservers() {
        viewModel.movieList.observe(viewLifecycleOwner) { movies ->
            mAdapter.submitList(movies)
            emptyState(empty = movies.isEmpty())
        }
    }

    private fun initRecycler() {
        mAdapter = DownloadMovieAdapter(
            context = requireContext(),
            detailsClickListener = { movieId ->
                movieId?.let {
                    val action = MainGraphDirections
                        .actionGlobalMovieDetailsFragment(movieId)
                    findNavController().navigate(action)
                }
            },
            deleteClickListener = { movie ->
                showBottomSheetDeleteMovie(movie)
            }
        )

        with(binding.rvMovies) {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = mAdapter
        }
    }

    private fun initSearchView() {
        binding.simpleSearchView.setOnQueryTextListener(object : SimpleSearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                //Log.d("SimpleSearchView", "Submit:$query")
                hideKeyboard()
                if (query.isNotEmpty()) {
                    //searchMovies(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

            override fun onQueryTextCleared(): Boolean {
                return false
            }
        })

        binding.simpleSearchView.setOnSearchViewListener(object : SimpleSearchView.SearchViewListener {
            override fun onSearchViewShown() {
            }

            override fun onSearchViewClosed() {
                //getMoviesByGenre()
            }

            override fun onSearchViewShownAnimation() {
            }

            override fun onSearchViewClosedAnimation() {
            }
        })
    }

    private fun showBottomSheetDeleteMovie(movie: Movie?) {
        val bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialog)
        val bottomSheetBinding = BottomSheetDeleteMovieBinding.inflate(
            layoutInflater, null, false
        )

        Glide
            .with(requireContext())
            .load("https://image.tmdb.org/t/p/w200${movie?.posterPath}")
            .into(bottomSheetBinding.ivMovie)

        bottomSheetBinding.textMovie.text = movie?.title
        bottomSheetBinding.textDuration.text = movie?.runtime?.calculateMovieTime()
        bottomSheetBinding.textSize.text = movie?.runtime?.toDouble()?.calculateFileSize()

        bottomSheetBinding.btnCancel.setOnClickListener { bottomSheetDialog.dismiss() }
        bottomSheetBinding.btnConfirm.setOnClickListener {
            bottomSheetDialog.dismiss()
            viewModel.deleteMovie(movie?.id)
        }

        bottomSheetDialog.setContentView(bottomSheetBinding.root)
        bottomSheetDialog.show();
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search_view, menu)
        val item = menu.findItem(R.id.action_search)
        binding.simpleSearchView.setMenuItem(item)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun emptyState(empty: Boolean) {
        binding.rvMovies.isVisible = !empty
        binding.layoutEmpty.isVisible = empty
    }

    /*
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    */

}