package com.argentinapesca.argentinapesca.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.navArgs
import com.argentinapesca.argentinapesca.R
import com.argentinapesca.argentinapesca.data.remote.newPost.newPostDataSource
import com.argentinapesca.argentinapesca.databinding.FragmentPostBinding
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem


class PostFragment : Fragment(R.layout.fragment_post) {

    private lateinit var binding: FragmentPostBinding
    private val args by navArgs<com.argentinapesca.argentinapesca.ui.home.PostFragmentArgs>()
    private val img_list = mutableListOf<CarouselItem>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentPostBinding.bind(view)
        binding.txtTitle.text = args.title
        //Glide.with(this).load(args.image).into(binding.imgPost)
        for (img in args.image.toList()){
          img_list.add(CarouselItem(img))
        }
        //img_list.add(CarouselItem(args.image))
        //img_list.add(CarouselItem("https://fotos.perfil.com/2021/05/21/cropped/696/522/center/2105-1176764.jpg"))
        binding.imgPost.addData(img_list)
        binding.txtPoster.text = "Usuario"
        binding.txtDescription.text = "Descripción: ${args.description}"

        /*Firebase.auth.signOut()
        //Log.d("user","${Firebase.auth.currentUser?.uid.toString()}")
        val new= newPostDataSource()
        GlobalScope.launch {
            new.createNewPost("pesca de lisa","https://eltubapesca.com.ar/wp-content/uploads/2020/01/El-Tuba-Pesca-Lisa.jpg","Pesca en el rio salado")
        }*/

    }
}