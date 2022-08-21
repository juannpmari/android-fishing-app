package com.argentinapesca.argentinapesca.ui.home

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.argentinapesca.argentinapesca.R
import com.argentinapesca.argentinapesca.data.model.UserData
import com.argentinapesca.argentinapesca.data.remote.auth.AuthDataSource
import com.argentinapesca.argentinapesca.data.remote.newPost.newPostDataSource
import com.argentinapesca.argentinapesca.databinding.FragmentPostBinding
import com.argentinapesca.argentinapesca.presentation.auth.AuthViewModel
import com.argentinapesca.argentinapesca.presentation.auth.AuthViewModelFactory
import com.argentinapesca.argentinapesca.repository.auth.AuthRepositoryImpl
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem


class PostFragment : Fragment(R.layout.fragment_post) {

    private lateinit var binding: FragmentPostBinding
    private val args by navArgs<com.argentinapesca.argentinapesca.ui.home.PostFragmentArgs>()
    private val img_list = mutableListOf<CarouselItem>()

    val viewModel by viewModels<AuthViewModel> {
        AuthViewModelFactory(
            AuthRepositoryImpl(
                AuthDataSource()
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentPostBinding.bind(view)
        binding.txtTitle.text = args.title
        //Glide.with(this).load(args.image).into(binding.imgPost)
        for (img in args.image.toList()) {
            img_list.add(CarouselItem(img))
        }
        //img_list.add(CarouselItem(args.image))
        //img_list.add(CarouselItem("https://fotos.perfil.com/2021/05/21/cropped/696/522/center/2105-1176764.jpg"))
        binding.imgPost.addData(img_list)
        binding.txtPoster.text = "Publicado por: " + args.posterName
        binding.txtPlace.text = "Ubicación: ${args.place}"
        binding.txtPrice.text = "Precio:  $${args.price}"
        binding.txtDescription.text = "Descripción: ${args.description}"
        binding.txtSpecies.text = "Especie buscada: ${args.species}"
        //Log.d("getUserInfo", "${args.poster}")

        viewModel.getUserInfo(args.poster).observe(viewLifecycleOwner) { result ->
            when (result) {
                is UserData -> {
                    binding.txtFaceLink.text = "Facebook: " + result.faceProfile
                    binding.txtPhone.text = "Teléfono: " + result.celular
                }
            }
        }

       // binding.btnDelete.isEnabled = args.poster == Firebase.auth.currentUser?.uid.toString()
        binding.btnDelete.isVisible = args.poster == Firebase.auth.currentUser?.uid.toString()
        binding.btnDelete.setOnClickListener {

            val builder = AlertDialog.Builder(this.requireContext())
            builder.setTitle("Confirmar eliminación")
            builder.setMessage("¿Desea eliminar el post?")

            builder.setPositiveButton("Eliminar post") { _, _ ->
                Firebase.firestore.collection("posts").document(args.id).delete()
                    .addOnCompleteListener {
                        findNavController().popBackStack()
                    }
            }
            builder.setNegativeButton("Cancelar") { _, _ ->
            }
            builder.show()


        }
    }
}