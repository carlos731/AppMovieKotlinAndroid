package com.mobiledevchtsca.movieapp.presenter.main.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.mobiledevchtsca.movieapp.databinding.FragmentHomeBinding
import com.mobiledevchtsca.movieapp.presenter.main.home.adapter.GenreMovieAdapter
import com.mobiledevchtsca.movieapp.util.StateView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var genreMovieAdapter: GenreMovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecycler()

        getGenres()
    }

    private fun initRecycler() {
        genreMovieAdapter = GenreMovieAdapter()

        with(binding.recyclerGenres) {
            setHasFixedSize(true)
            adapter = genreMovieAdapter
        }
    }

    private fun getGenres() {
        viewModel.getGenres().observe(viewLifecycleOwner) { stateView ->
            when(stateView) {
                is StateView.Loading -> {

                }
                is StateView.Success -> {
                    genreMovieAdapter.submitList(stateView.data)
                }
                is StateView.Error -> {
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