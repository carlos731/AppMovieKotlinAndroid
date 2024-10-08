package com.mobiledevchtsca.movieapp.presenter.main.moviedetails.details

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobiledevchtsca.movieapp.presenter.main.moviedetails.adapter.ViewPagerAdapter
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.mobiledevchtsca.movieapp.R
import com.mobiledevchtsca.movieapp.databinding.DialogDownloadingBinding
import com.mobiledevchtsca.movieapp.databinding.FragmentMovieDetailsBinding
import com.mobiledevchtsca.movieapp.domain.model.Movie
import com.mobiledevchtsca.movieapp.presenter.main.moviedetails.adapter.CastAdapter
import com.mobiledevchtsca.movieapp.presenter.main.moviedetails.comments.ComentsFragment
import com.mobiledevchtsca.movieapp.presenter.main.moviedetails.similar.SimilarFragment
import com.mobiledevchtsca.movieapp.presenter.main.moviedetails.trailers.TrailersFragment
import com.mobiledevchtsca.movieapp.util.StateView
import com.mobiledevchtsca.movieapp.util.ViewPager2ViewHeightAnimator
import com.mobiledevchtsca.movieapp.util.calculateFileSize
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
    private lateinit var dialogDownloading: AlertDialog

    private lateinit var movie: Movie

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

        configTabLayout()

        initListeners()
    }

    private fun initListeners() {
        binding.btnDownloading.setOnClickListener { showDialogDownloading() }
    }

    private fun configTabLayout() {
        viewModel.setMovieId(movieId = args.movieId)

        val adapter = ViewPagerAdapter(requireActivity())
        val mViewPager = ViewPager2ViewHeightAnimator()

        mViewPager.viewPager2 = binding.viewPager
        mViewPager.viewPager2?.adapter = adapter


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

        mViewPager.viewPager2?.let { viewPager2 ->
            TabLayoutMediator(
                binding.tabs, viewPager2
            ) { tab, position ->
                tab.text = getString(adapter.getTitle(position))
            }.attach()
        }

    }

    private fun getMovieDetails() {
        viewModel.getMovieDetails(movieId = args.movieId).observe(viewLifecycleOwner) { stateView ->
            when (stateView) {
                is StateView.Loading -> {

                }
                is StateView.Success -> {
                    stateView.data?.let {
                        this.movie = it
                        configData()
                    }

                    //configTabLayout()
                }
                is StateView.Error -> {
                }
            }
        }
    }

    private fun insertMovie() {
        viewModel.insertMovie(movie).observe(viewLifecycleOwner) { stateView ->
            when (stateView) {
                is StateView.Loading -> {

                }

                is StateView.Success -> {
                    configData()
                }

                is StateView.Error -> {

                }
            }
        }
    }

    private fun getCredits() {
        viewModel.getCredits(movieId = args.movieId).observe(viewLifecycleOwner) { stateView ->
            when (stateView) {
                is StateView.Loading -> {

                }
                is StateView.Success -> {
                    castAdapter.submitList(stateView.data?.cast)
                }
                is StateView.Error -> {

                }
            }
        }
    }

    private fun configData() {
        Glide
            .with(requireContext())
            .load("https://image.tmdb.org/t/p/w500${movie.backdropPath}")
            .into(binding.imageMovie)

        binding.textMovie.text = movie.title

        binding.textVoteAverage.text = String.format("%.1f", movie.voteAverage)

        val originalFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ROOT)
        val data = originalFormat.parse(movie.releaseDate ?: "")

        val yearFormat = SimpleDateFormat("yyyy", Locale.ROOT)
        val year = data?.let { yearFormat.format(it) }

        binding.textReleaseDate.text = year

        binding.textProductionCountry.text = movie.productionCountries?.get(0)?.name ?: ""

        val genres = movie.genres?.map { it.name }?.joinToString(", ")
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

    private fun showDialogDownloading() {
        /* Looper do progressIndicator */
        val dialogBinding = DialogDownloadingBinding.inflate(LayoutInflater.from(requireContext()))
        var progress = 0
        var downloaded = 0.0
        val movieDuration = movie?.runtime?.toDouble() ?: 0.0

        val handle = Handler(Looper.getMainLooper())
        val runnable = object : Runnable {
            override fun run() {
                if (progress < 100) {
                    downloaded += (movieDuration / 100.0)
                    dialogBinding.textDownloading.text = getString(
                        R.string.text_downloaded_size_dialog_downloading,
                        downloaded.calculateFileSize(),
                        movieDuration.calculateFileSize()
                    )

                    progress++
                    dialogBinding.progressIndicator.progress = progress
                    dialogBinding.textProgress.text = getString(
                        R.string.text_download_progress_dialog_downloading,
                        progress
                    )

                    handle.postDelayed(this, 50)
                } else {
                    insertMovie()
                    dialogDownloading.dismiss()
                }
            }
        }
        handle.post(runnable)
        /* Looper do progressIndicator */

        val builder = AlertDialog.Builder(requireContext(), R.style.CustomAlertDialog)
        builder.setView(dialogBinding.root)

        dialogBinding.btnHide.setOnClickListener { dialogDownloading.dismiss() }
        dialogBinding.ibCancel.setOnClickListener { dialogDownloading.dismiss() }

        dialogDownloading = builder.create()
        dialogDownloading.show()
    }

    /*
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    */

}