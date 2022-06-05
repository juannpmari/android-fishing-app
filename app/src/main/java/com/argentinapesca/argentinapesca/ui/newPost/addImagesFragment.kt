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
        var imageUriList= mutableListOf<Uri>()
        var imageUri by Delegates.observable(Uri.EMPTY) { prop, old, new ->
            //Log.d("uri", "Nuevo valor $new")
            binding.imgGallery.setImageURI(new)
            imageUriList.add(new)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentAddImagesBinding.bind(view)
        var bitmapList= mutableListOf<Bitmap>()

        binding.btnGallery.setOnClickListener {
            resultLauncher.launch("image/*")
        }

       binding.btnCreate.setOnClickListener {
            for (img in imageUriList){
                bitmapList.add(MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), img))
            }
            viewModel.createNewPost(
                args.title,
                args.description,
                bitmapList,
                args.place,
                args.faceLink
            ).observe(viewLifecycleOwner, Observer {
            })
            findNavController().popBackStack(R.id.newPostFragment,inclusive = true)
        }

    }

    var resultLauncher: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.GetContent(), object : ActivityResultCallback<Uri> {
            override fun onActivityResult(result: Uri?) {
                if (result != null) {
                    imageUri =result
                }
            }
        })

/*object Callback : ActivityResultCallback<Uri> {
    override fun onActivityResult(result: Uri?) {
        if (result != null) {
            imageUri =result
        }
    }

}*/
}