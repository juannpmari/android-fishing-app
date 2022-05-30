package com.argentinapesca.argentinapesca.ui.newPost

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.argentinapesca.argentinapesca.R
import com.argentinapesca.argentinapesca.data.model.Post
import com.argentinapesca.argentinapesca.data.remote.home.DataSource
import com.argentinapesca.argentinapesca.data.remote.newPost.newPostDataSource
import com.argentinapesca.argentinapesca.databinding.FragmentNewPostBinding
import com.argentinapesca.argentinapesca.presentation.home.PostViewModel
import com.argentinapesca.argentinapesca.presentation.home.PostViewModelFactory
import com.argentinapesca.argentinapesca.presentation.newPost.newPostViewModel
import com.argentinapesca.argentinapesca.presentation.newPost.newPostViewModelFactory
import com.argentinapesca.argentinapesca.repository.home.RepositoryImpl
import com.argentinapesca.argentinapesca.repository.newPost.newPostRepository
import com.argentinapesca.argentinapesca.repository.newPost.newPostRepositoryImpl

class newPostFragment : Fragment(R.layout.fragment_new_post) {

    private val viewModel by viewModels<newPostViewModel> {
        newPostViewModelFactory(
            newPostRepositoryImpl(
                newPostDataSource()
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentNewPostBinding.bind(view)
        val img = mutableListOf<String>()
        binding.btnCreate.setOnClickListener {
            img.add(binding.editImage1.text.toString())
            img.add(binding.editImage2.text.toString())
            viewModel.createNewPost(
                binding.editTitle.text.toString(),
                img,
                binding.editDescription.text.toString()
            ).observe(viewLifecycleOwner, Observer {
            })
            findNavController().popBackStack()
        }
    }
}