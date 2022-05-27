package com.argentinapesca.argentinapesca.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.argentinapesca.argentinapesca.R
import com.argentinapesca.argentinapesca.data.model.Post
import com.argentinapesca.argentinapesca.data.remote.DataSource
import com.argentinapesca.argentinapesca.databinding.FragmentPostBinding
import com.argentinapesca.argentinapesca.presentation.PostViewModel
import com.argentinapesca.argentinapesca.presentation.PostViewModelFactory
import com.argentinapesca.argentinapesca.repository.RepositoryImpl
import com.bumptech.glide.Glide


class PostFragment : Fragment(R.layout.fragment_post) {

    private lateinit var binding: FragmentPostBinding
    private val viewModel by viewModels<PostViewModel> { PostViewModelFactory(RepositoryImpl(DataSource())) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPostBinding.bind(view)

        viewModel.fetchPost().observe(viewLifecycleOwner, Observer {
            binding.txtTitle.text = it[0].title
            Glide.with(this).load(it[0].image).into(binding.imgPost)
        })

    }
}