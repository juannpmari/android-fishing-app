package com.argentinapesca.argentinapesca.ui.newPost

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.argentinapesca.argentinapesca.R
import com.argentinapesca.argentinapesca.databinding.FragmentNewPostBinding



class newPostFragment : Fragment(R.layout.fragment_new_post) {

    private lateinit var binding: FragmentNewPostBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNewPostBinding.bind(view)

        binding.btnNext.setOnClickListener {
            val action = newPostFragmentDirections.actionNewPostFragmentToAddImagesFragment(
                binding.editTitle.text.toString(),
                binding.editPlace.text.toString(),
                binding.editDescription.text.toString(),
                binding.editFaceLink.text.toString()
            )
            findNavController().navigate(action)
        }
    }


}

