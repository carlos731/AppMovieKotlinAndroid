package com.mobiledevchtsca.movieapp.presenter.main.movie_details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.hellodev.movieapp.presenter.main.moviedetails.adapter.ViewPagerAdapter
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.mobiledevchtsca.movieapp.R
import com.mobiledevchtsca.movieapp.databinding.FragmentMovieDetailsBinding
import com.mobiledevchtsca.movieapp.domain.model.Movie
import com.mobiledevchtsca.movieapp.presenter.main.movie_details.adapter.CastAdapter
import com.mobiledevchtsca.movieapp.presenter.main.movie_details.tabs.ComentsFragment
import com.mobiledevchtsca.movieapp.presenter.main.movie_details.tabs.SimilarFragment
import com.mobiledevchtsca.movieapp.presenter.main.movie_details.tabs.TrailersFragment
import com.mobiledevchtsca.movieapp.presenter.main.movie_details.viewmodel.MovieDetailsViewModel
import com.mobiledevchtsca.movieapp.util.StateView
import com.mobiledevchtsca.movieapp.util.initToolbar
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Locale

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    private val args: MovieDetailsFragmentArgs by navArgs();

    private val viewModel: MovieDetailsViewModel by activityViewModels()

    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var castAdapter: CastAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(binding.toolbar, lightIcon = true)

        getMovieDetails()

        initRecyclerCredits()

        //configTabLayout()
    }

    private fun configTabLayout() {
        viewModel.setMovieId(movieId = args.movieId)

        val adapter = ViewPagerAdapter(requireActivity())
        binding.viewPager.adapter = adapter

        adapter.addFragment(
            fragment = TrailersFragment(),
            title = R.string.title_trailers_tab_layout
        )

        adapter.addFragment(
            fragment = SimilarFragment(),
            title = R.string.title_similares_tab_layout
        )

        adapter.addFragment(
            fragment = ComentsFragment(),
            title = R.string.title_comentarios_tab_layout
        )

        binding.viewPager.offscreenPageLimit = adapter.itemCount

        TabLayoutMediator(
            binding.tabs, binding.viewPager
        ) { tab, position ->
            tab.text = getString(adapter.getTitle(position))
        }.attach()

    }

    private fun getMovieDetails() {
        viewModel.getMovieDetails(movieId = args.movieId).observe(viewLifecycleOwner) { stateView ->
            when (stateView) {
                is StateView.Loading -> {
                    binding.progressBar.isVisible = true
                    binding.horizontalScrollView.isVisible = false
                    binding.groupButtons1.isVisible = false
                    binding.groupButtons2.isVisible = false
                }
                is StateView.Success -> {
                    binding.progressBar.isVisible = false
                    configData(movie = stateView.data)
                    binding.horizontalScrollView.isVisible = true
                    binding.groupButtons1.isVisible = true
                    binding.groupButtons2.isVisible = true
                    configTabLayout()
                }
                is StateView.Error -> {
                    binding.progressBar.isVisible = false
                }
            }
        }
    }

    private fun getCredits() {
        viewModel.getCredits(movieId = args.movieId).observe(viewLifecycleOwner) { stateView ->
            when (stateView) {
                is StateView.Loading -> {
                    binding.progressCast.isVisible = true
                }
                is StateView.Success -> {
                    binding.progressCast.isVisible = false
                    castAdapter.submitList(stateView.data?.cast)
                }
                is StateView.Error -> {

                }
            }
        }
    }

    private fun configData(movie: Movie?) {
        Glide
            .with(requireContext())
            .load("https://image.tmdb.org/t/p/w500${movie?.posterPath}")
            .into(binding.imageMovie)

        binding.textMovie.text = movie?.title

        binding.textVoteAverage.text = String.format("%.1f", movie?.voteAverage)

        val originalFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ROOT)
        val data = originalFormat.parse(movie?.releaseDate ?: "")

        val yearFormat = SimpleDateFormat("yyyy", Locale.ROOT)
        val year = yearFormat.format(data)

        binding.textReleaseDate.text = year

        binding.textProductionCountry.text = movie?.productionCountries?.get(0)?.name ?: ""

        val genres = movie?.genres?.map { it.name }?.joinToString(", ")
        binding.textGenres.text = getString(R.string.text_all_genres_movie_details_fragment, genres)

        binding.textDescription.text = getString(R.string.text_all_description_movie_details_fragment, movie?.overview)

        getCredits()
    }

    private fun initRecyclerCredits() {
        castAdapter = CastAdapter()

        with(binding.recyclerCast) {
            //setHasFixedSize(true) // Não pode usar quando o item não tem tamanho fixo
            layoutManager = LinearLayoutManager(
                requireContext(), LinearLayoutManager.HORIZONTAL, false
            )
            adapter = castAdapter
        }
    }

    /*
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    */

}