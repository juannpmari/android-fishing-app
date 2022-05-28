package com.argentinapesca.argentinapesca.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.argentinapesca.argentinapesca.R
import com.argentinapesca.argentinapesca.data.model.Post
import com.argentinapesca.argentinapesca.data.remote.home.DataSource
import com.argentinapesca.argentinapesca.databinding.FragmentMainScreenBinding
import com.argentinapesca.argentinapesca.databinding.PostItemBinding
import com.argentinapesca.argentinapesca.presentation.home.PostViewModel
import com.argentinapesca.argentinapesca.presentation.home.PostViewModelFactory
import com.argentinapesca.argentinapesca.repository.home.RepositoryImpl
import com.bumptech.glide.Glide

class MainScreenFragment : Fragment(R.layout.fragment_main_screen),MainScreenAdapter.OnClickListener {

    private lateinit var binding: FragmentMainScreenBinding
    private lateinit var adapter: MainScreenAdapter

    private val viewModel by viewModels<PostViewModel> {
        PostViewModelFactory(
            RepositoryImpl(
                DataSource()
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding= FragmentMainScreenBinding.bind(view)

        val bindingInterface = object : RecyclerBindingInterface {
            override fun bindData(item: Post, view: View) {
                val itemBinding = PostItemBinding.bind(view)
                itemBinding.txtTitle.text = item.title
                Glide.with(context!!).load(item.image).into(itemBinding.imgPost)
            }
        }
        viewModel.fetchPost().observe(viewLifecycleOwner, Observer {
            adapter = MainScreenAdapter(it, bindingInterface,this@MainScreenFragment)
            binding.rvMainScreen.adapter=adapter
        })
    }

    override fun onClick(item: Post) {
        //Log.d("click","clickeado post ${item.title}")
        val action=MainScreenFragmentDirections.actionMainScreenFragmentToPostFragment(item.title,item.image,item.description)
        findNavController().navigate(action)
    }
}