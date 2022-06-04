package com.argentinapesca.argentinapesca.ui.home

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
import com.argentinapesca.argentinapesca.ui.MainScreenAdapter
import com.argentinapesca.argentinapesca.ui.RecyclerBindingInterface
import com.bumptech.glide.Glide
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainScreenFragment : Fragment(R.layout.fragment_main_screen),
    MainScreenAdapter.OnClickListener {

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
        val auth = Firebase.auth

        if (Firebase.auth.currentUser != null) requireActivity().findViewById<NavigationView>(R.id.navView).menu.findItem(
            R.id.proveedor
        ).setTitle("Cambiar de cuenta")
        else requireActivity().findViewById<NavigationView>(R.id.navView).menu.findItem(R.id.proveedor)
            .setTitle("Ingresar")


        binding = FragmentMainScreenBinding.bind(view)

        val bindingInterface = object : RecyclerBindingInterface {
            override fun bindData(item: Post, view: View) {
                val itemBinding = PostItemBinding.bind(view)
                itemBinding.txtTitle.text = item.title
                if(item.image.size>0){
                    Glide.with(context!!).load(item.image[0]).into(itemBinding.imgPost)
                }

            }
        }
        viewModel.fetchPost().observe(viewLifecycleOwner, Observer {
            //adapter = MainScreenAdapter(it, bindingInterface,this@MainScreenFragment)
            if (auth.currentUser != null) {
                adapter = MainScreenAdapter(
                    it.filter { s -> s.poster == auth.currentUser?.uid },
                    bindingInterface,
                    this@MainScreenFragment
                )
            } else adapter = MainScreenAdapter(it, bindingInterface, this@MainScreenFragment)
            binding.rvMainScreen.adapter = adapter
        })
    }

    override fun onClick(item: Post) {
        //Log.d("click","clickeado post ${item.title}")
        val action =
            com.argentinapesca.argentinapesca.ui.home.MainScreenFragmentDirections.actionMainScreenFragmentToPostFragment(
                item.title,
                item.image.toTypedArray(),
                item.description
            )
        findNavController().navigate(action)
    }
}