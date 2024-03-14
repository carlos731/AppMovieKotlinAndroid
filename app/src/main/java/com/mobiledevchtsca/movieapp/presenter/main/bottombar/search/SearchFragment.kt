package com.mobiledevchtsca.movieapp.presenter.main.bottombar.search

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import br.com.hellodev.movieapp.presenter.main.moviegenre.adapter.LoadStatePagingAdapter
import com.mobiledevchtsca.movieapp.MainGraphDirections
import com.mobiledevchtsca.movieapp.R
import com.mobiledevchtsca.movieapp.databinding.FragmentSearchBinding
import com.mobiledevchtsca.movieapp.presenter.main.bottombar.home.adapter.MovieAdapter
import com.mobiledevchtsca.movieapp.presenter.main.moviegenre.adapter.MoviePagingAdapter
import com.mobiledevchtsca.movieapp.util.StateView
import com.mobiledevchtsca.movieapp.util.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchViewModel by viewModels()

    private lateinit var moviePagingAdapter: MoviePagingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecycler()

        initSearchView()

        //initObservers()
    }

    /*
    private fun initObservers() {
        //stateObserver()

        searchObserver()
    }
    */

    private fun initRecycler() {
        moviePagingAdapter = MoviePagingAdapter(
            context = requireContext(),
            movieClickListener = { movieId ->
                movieId?.let {
                    val action = MainGraphDirections
                        .actionGlobalMovieDetailsFragment(movieId)
                    findNavController().navigate(action)
                }
            }
        )

        lifecycleScope.launch {
            moviePagingAdapter.loadStateFlow.collectLatest { loadState ->
                when (loadState.refresh) {
                    is LoadState.Loading -> {
                        binding.recyclerMovies.isVisible = false
                        //binding.progressBar.isVisible = true
                        binding.shimmer.isVisible = true
                        binding.shimmer.startShimmer()
                    }
                    is LoadState.NotLoading -> {
                        binding.shimmer.stopShimmer()
                        binding.shimmer.isVisible = false
                        binding.recyclerMovies.isVisible = true
                        //binding.progressBar.isVisible = false
                    }
                    is LoadState.Error -> {
                        binding.recyclerMovies.isVisible = false
                        //binding.progressBar.isVisible = false
                        binding.shimmer.stopShimmer()
                        binding.shimmer.isVisible = false
                        val error = (loadState.refresh as LoadState.Error).error.message ?: "Ocorreu um erro, tente novamente mais tarde."
                        Toast.makeText(requireActivity(), error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        with(binding.recyclerMovies) {
            setHasFixedSize(true)
            val gridLayoutManager = GridLayoutManager(requireContext(), 2)
            layoutManager = gridLayoutManager

            val footerAdapter = moviePagingAdapter.withLoadStateFooter(
                footer = LoadStatePagingAdapter()
            )

            adapter = footerAdapter

            gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (position == moviePagingAdapter.itemCount && footerAdapter.itemCount > 0) {
                        2
                    } else {
                        1
                    }
                }
            }
        }

    }

    private fun initSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                //Log.d("SimpleSearchView", "Submit:$query")
                hideKeyboard()
                if (query.isNotEmpty()) {
                    searchMovies(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                Log.d("SimpleSearchView", "Text changed:$newText")
                return false
            }
        })
    }

    private fun searchMovies(query: String?) {
        lifecycleScope.launch {
            viewModel.searchMovies(query).collectLatest {pagingData ->
                moviePagingAdapter.submitData(viewLifecycleOwner.lifecycle ,pagingData)
            }
        }
    }

    /* // Desnecessário após o uso do lifecycleScope.launch com o shimmer
    private fun stateObserver() {
        viewModel.searchState.observe(viewLifecycleOwner) { stateView ->
            when(stateView) {
                is StateView.Loading -> {
                    binding.recyclerMovies.isVisible = false
                    binding.progressBar.isVisible = true
                }
                is StateView.Success -> {
                    binding.progressBar.isVisible = false
                    binding.recyclerMovies.isVisible = true
                }
                is StateView.Error -> {
                    binding.progressBar.isVisible = false
                }
            }
        }
    }
    */

    /*
    private fun searchObserver() {
        viewModel.movieList.observe(viewLifecycleOwner) { movieList ->
            movieAdapter.submitList(movieList)
            emptyState(empty = movieList.isEmpty())
        }
    }
    */

    private fun emptyState(empty: Boolean) {
        binding.recyclerMovies.isVisible = !empty
        binding.layoutEmpty.isVisible = empty
    }

    /*
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    */

}