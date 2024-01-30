package com.mobiledevchtsca.movieapp.presenter.main.bottombar.search

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.mobiledevchtsca.movieapp.MainGraphDirections
import com.mobiledevchtsca.movieapp.R
import com.mobiledevchtsca.movieapp.databinding.FragmentSearchBinding
import com.mobiledevchtsca.movieapp.presenter.main.bottombar.home.adapter.MovieAdapter
import com.mobiledevchtsca.movieapp.util.StateView
import com.mobiledevchtsca.movieapp.util.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchViewModel by viewModels()

    private lateinit var movieAdapter: MovieAdapter

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
    }

    private fun initRecycler() {
        movieAdapter = MovieAdapter(
            context = requireContext(),
            layoutInflater = R.layout.movie_genre_item,
            movieClickListener = { movieId ->
                movieId?.let {
                    val action = MainGraphDirections
                        .actionGlobalMovieDetailsFragment(movieId)
                    findNavController().navigate(action)
                }
            }
        )

        with(binding.recyclerMovies) {
            layoutManager = GridLayoutManager(requireContext(), 2)
            setHasFixedSize(true)
            adapter = movieAdapter
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
        viewModel.searchMovies(query).observe(viewLifecycleOwner) { stateView ->
            when(stateView) {
                is StateView.Loading -> {
                    binding.recyclerMovies.isVisible = false
                    binding.progressBar.isVisible = true
                }
                is StateView.Success -> {
                    binding.progressBar.isVisible = false
                    movieAdapter.submitList(stateView.data)
                    binding.recyclerMovies.isVisible = true
                }
                is StateView.Error -> {
                    binding.progressBar.isVisible = false
                }
            }
        }
    }

    /*
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    */

}