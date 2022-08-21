package com.argentinapesca.argentinapesca.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.argentinapesca.argentinapesca.R
import com.argentinapesca.argentinapesca.data.remote.auth.AuthDataSource
import com.argentinapesca.argentinapesca.databinding.FragmentEditProfileBinding
import com.argentinapesca.argentinapesca.databinding.FragmentProfileBinding
import com.argentinapesca.argentinapesca.databinding.FragmentSignUpBinding
import com.argentinapesca.argentinapesca.presentation.auth.AuthViewModel
import com.argentinapesca.argentinapesca.presentation.auth.AuthViewModelFactory
import com.argentinapesca.argentinapesca.repository.auth.AuthRepositoryImpl
import com.argentinapesca.argentinapesca.ui.auth.SignUpFragment

class editProfileFragment : Fragment(R.layout.fragment_edit_profile) {

    val viewModel by viewModels<AuthViewModel> {
        AuthViewModelFactory(
            AuthRepositoryImpl(
                AuthDataSource()
            )
        )
    }

    private lateinit var binding: FragmentEditProfileBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentEditProfileBinding.bind(view)

        binding.btnSave.setOnClickListener {
            val username = binding.editUsername.text.toString().lowercase()
            val faceProfile = binding.editFace.text.toString()
            val celular = binding.editPhone.text.toString()
            if (validatePhone(celular)){
            viewModel.editUserInfo(username, faceProfile, celular).observe(viewLifecycleOwner) {
            }
            Toast.makeText(this.requireContext(), "Información actualizada", Toast.LENGTH_SHORT)
                .show()
            findNavController().popBackStack()
            //findNavController().popBackStack() //Si quiero volver al MainScreen
            }
        }
    }


    private fun validatePhone(phone: String): Boolean {
        if (phone.isNotEmpty() && phone.toIntOrNull() == null) {
            binding.editPhone.error = "Ingrese solamente números"
            return false
        }
        if (phone.isNotEmpty() && phone.length < 8) {
            binding.editPhone.error = "Número incompleto"
            return false
        }
        return true
    }
}