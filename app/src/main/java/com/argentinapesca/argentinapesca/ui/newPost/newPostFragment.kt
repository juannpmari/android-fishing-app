package com.argentinapesca.argentinapesca.ui.newPost

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.argentinapesca.argentinapesca.R
import com.argentinapesca.argentinapesca.data.remote.newPost.newPostDataSource
import com.argentinapesca.argentinapesca.databinding.FragmentNewPostBinding
import com.argentinapesca.argentinapesca.presentation.newPost.newPostViewModel
import com.argentinapesca.argentinapesca.presentation.newPost.newPostViewModelFactory
import com.argentinapesca.argentinapesca.repository.newPost.newPostRepositoryImpl
import kotlin.properties.Delegates


class newPostFragment : Fragment(R.layout.fragment_new_post) {

    private val viewModel by viewModels<newPostViewModel> {
        newPostViewModelFactory(
            newPostRepositoryImpl(
                newPostDataSource()
            )
        )
    }

    companion object {
        private lateinit var binding: FragmentNewPostBinding
        var imageUriList= mutableListOf<Uri>()
        var imageUri by Delegates.observable(Uri.EMPTY) { prop, old, new ->
            //Log.d("uri", "Nuevo valor $new")
            binding.imgGallery.setImageURI(new)
            imageUriList.add(new)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNewPostBinding.bind(view)
        var bitmapList= mutableListOf<Bitmap>()

        binding.btnGallery.setOnClickListener {
            resultLauncher.launch("image/*")
            /*if (imageUri != Uri.EMPTY) {
                //Log.d("uri", "2 uri: ${imageUri.toString()}")
            }*/
        }

        binding.btnCreate.setOnClickListener {
            for (img in imageUriList){
                bitmapList.add(MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), img))
            }
            viewModel.createNewPost(
                binding.editTitle.text.toString(),
                binding.editDescription.text.toString(),
                bitmapList,
                binding.editPlace.text.toString(),
                binding.editFaceLink.text.toString()
            ).observe(viewLifecycleOwner, Observer {
            })
            findNavController().popBackStack()
        }
    }

    var resultLauncher: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.GetContent(), Callback)

    object Callback : ActivityResultCallback<Uri> {
        override fun onActivityResult(result: Uri?) {
            if (result != null) {
                imageUri=result
            }
        }
    }
}

