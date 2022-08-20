package com.argentinapesca.argentinapesca.ui.newPost

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.argentinapesca.argentinapesca.R
import com.argentinapesca.argentinapesca.data.remote.newPost.newPostDataSource
import com.argentinapesca.argentinapesca.databinding.FragmentAddImagesBinding
import com.argentinapesca.argentinapesca.databinding.FragmentNewPostBinding
import com.argentinapesca.argentinapesca.presentation.newPost.newPostViewModel
import com.argentinapesca.argentinapesca.presentation.newPost.newPostViewModelFactory
import com.argentinapesca.argentinapesca.repository.newPost.newPostRepositoryImpl
import kotlin.properties.Delegates

class addImagesFragment : Fragment(R.layout.fragment_add_images) {
    private val viewModel by viewModels<newPostViewModel> {
        newPostViewModelFactory(
            newPostRepositoryImpl(
                newPostDataSource()
            )
        )
    }
    private val args by navArgs<addImagesFragmentArgs>()

    companion object {
        private lateinit var binding: FragmentAddImagesBinding
        private var img_flag: Int = 0
        var imageUriList = mutableListOf<Uri>()

        var imageUri_11 by Delegates.observable(Uri.EMPTY) { prop, old, new ->
            binding.img11.setImageURI(new)
            imageUriList.add(new)
        }
        var imageUri_12 by Delegates.observable(Uri.EMPTY) { prop, old, new ->
            binding.img12.setImageURI(new)
            imageUriList.add(new)
        }
        var imageUri_21 by Delegates.observable(Uri.EMPTY) { prop, old, new ->
            binding.img21.setImageURI(new)
            imageUriList.add(new)
        }
        var imageUri_22 by Delegates.observable(Uri.EMPTY) { prop, old, new ->
            binding.img22.setImageURI(new)
            imageUriList.add(new)
        }
        var imageUri_31 by Delegates.observable(Uri.EMPTY) { prop, old, new ->
            binding.img31.setImageURI(new)
            imageUriList.add(new)
        }
        var imageUri_32 by Delegates.observable(Uri.EMPTY) { prop, old, new ->
            binding.img32.setImageURI(new)
            imageUriList.add(new)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentAddImagesBinding.bind(view)
        var bitmapList = mutableListOf<Bitmap>()

        /*binding.btnGallery.setOnClickListener {
            resultLauncher.launch("image/*")
        }*/*/
        binding.img11.setOnClickListener {
            img_flag = 11
            resultLauncher.launch("image/*")
        }

        binding.img12.setOnClickListener {
            img_flag = 12
            resultLauncher.launch("image/*")
        }

        binding.img21.setOnClickListener {
            img_flag = 21
            resultLauncher.launch("image/*")
        }

        binding.img22.setOnClickListener {
            img_flag = 22
            resultLauncher.launch("image/*")
        }

        binding.img31.setOnClickListener {
            img_flag = 31
            resultLauncher.launch("image/*")
        }

        binding.img32.setOnClickListener {
            img_flag = 32
            resultLauncher.launch("image/*")
        }


        binding.btnCreate.setOnClickListener {
            for (img in imageUriList) {
                bitmapList.add(
                    MediaStore.Images.Media.getBitmap(
                        requireActivity().getContentResolver(),
                        img
                    )
                )
            }
            viewModel.createNewPost(
                args.title,
                args.description,
                bitmapList,
                args.place,
                args.price
            ).observe(viewLifecycleOwner, Observer {
            })
            findNavController().popBackStack(R.id.newPostFragment, inclusive = true)
        }

    }

    var resultLauncher: ActivityResultLauncher<String> =
        registerForActivityResult(
            ActivityResultContracts.GetContent(),
            object : ActivityResultCallback<Uri> {
                override fun onActivityResult(result: Uri?) {
                    //if (result != null) {
                    when (img_flag) {
                        11 -> {
                            imageUri_11 = result
                            img_flag = 0
                        }
                        12 -> {
                            imageUri_12 = result
                            img_flag = 0
                        }
                        21 -> {
                            imageUri_21 = result
                            img_flag = 0
                        }
                        22 -> {
                            imageUri_22 = result
                            img_flag = 0
                        }
                        31 -> {
                            imageUri_31 = result
                            img_flag = 0
                        }
                        32 -> {
                            imageUri_32 = result
                            img_flag = 0
                        }
                    }

                }
            })
}