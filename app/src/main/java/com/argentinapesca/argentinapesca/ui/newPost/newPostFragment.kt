package com.argentinapesca.argentinapesca.ui.newPost

import android.os.Bundle
import android.util.Log
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
            var title = binding.editTitle.text.toString()
            var place = binding.editPlace.text.toString().lowercase()
            var price = binding.editPrice.text.toString()
            val b1 = validateTitle(title)
            val b2 = validatePlace(place)
            val b3 = validatePrice(price)
            if (b1 && b2 && b3) {
                val action = newPostFragmentDirections.actionNewPostFragmentToAddImagesFragment(
                    //binding.editTitle.text.toString(),
                    //binding.editPlace.text.toString().lowercase(),
                    title,
                    place,
                    binding.editDescription.text.toString(),
                    //binding.editPrice.text.toString(),
                    price,
                    binding.editSpecies.text.toString().lowercase()
                )
                findNavController().navigate(action)
            }
        }
    }

    private fun validateTitle(title: String): Boolean {
        if (title.isEmpty()) {
            binding.editTitle.error = "Ingrese un título"
            return false
        } else return true
    }

    private fun validatePlace(place: String): Boolean {
        if (place.isEmpty()) {
            binding.editPlace.error = "Ingrese una ubicación"
            return false
        } else return true
    }

    private fun validatePrice(price: String): Boolean {
        if (price.isNotEmpty() && price.toIntOrNull() == null) {
            binding.editPrice.error = "Ingrese un valor numérico"
            return false
        } else return true
    }

}

