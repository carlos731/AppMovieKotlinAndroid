package com.mobiledevchtsca.movieapp.presenter.main.moviedetails.comments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mobiledevchtsca.movieapp.databinding.FragmentComentsBinding
import com.mobiledevchtsca.movieapp.presenter.main.moviedetails.adapter.CommentsAdapter
import com.mobiledevchtsca.movieapp.domain.model.AuthorDetails
import com.mobiledevchtsca.movieapp.domain.model.MovieReview
import com.mobiledevchtsca.movieapp.util.formatCommentDate

class ComentsFragment : Fragment() {

    private var _binding: FragmentComentsBinding? = null
    private val binding get() = _binding!!

    private lateinit var commentsAdapter: CommentsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentComentsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecycler()

        commentsAdapter.submitList(fakeList())
    }

    private fun initRecycler() {
        commentsAdapter = CommentsAdapter()

        with(binding.recyclerComments) {
            adapter = commentsAdapter
        }
    }

    private fun fakeList(): List<MovieReview> {
        return listOf(
            MovieReview(
                author = "thealanfrench",
                authorDetails = AuthorDetails(
                    name = "",
                    username = "thealanfrench",
                    avatarPath = "https://image.tmdb.org/t/p/w500/yHGV91jVzmqpFOtRSHF0avBZmPm.jpg",
                    rating = 5
                ),
                content = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.",
                createdAt = "2023-03-15T05:13:49.138Z", //formatCommentDate("2023-03-15T05:13:49.138Z"),
                id = "6411540dfe6c1800bb659ebd",
                updatedAt = "2023-03-15T05:13:49.138Z",
                url = "https://www.themoviedb.org/review/6411540dfe6c1800bb659ebd"
            )
        )
    }

//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }

}