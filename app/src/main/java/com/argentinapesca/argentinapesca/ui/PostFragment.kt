package com.argentinapesca.argentinapesca.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.navArgs
import com.argentinapesca.argentinapesca.R
import com.argentinapesca.argentinapesca.databinding.FragmentPostBinding
import com.bumptech.glide.Glide


class PostFragment : Fragment(R.layout.fragment_post) {

    private lateinit var binding: FragmentPostBinding
    private val args by navArgs<PostFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentPostBinding.bind(view)
        binding.txtTitle.text = args.title
        Glide.with(this).load(args.image).into(binding.imgPost)
        binding.txtDescription.text="Descripción: ${args.description}"

/*        val bindingInterface = object : RecyclerBindingInterface {
            override fun bindData(item: Post, view: View) {
                val itemBinding = FragmentPostBinding.bind(view)
                itemBinding.txtTitle.text = item.title
                //Glide.with(this).load(item.image).into(binding.imgPost)
            }
        }

        viewModel.fetchPost().observe(viewLifecycleOwner, Observer {
            //binding.txtTitle.text = it[1].title
            //Glide.with(this).load(it[1].image).into(binding.imgPost)
            adapter = MainScreenAdapter(it, bindingInterface)
            binding.rvMainScreen.adapter=adapter
        })*/

    }
}