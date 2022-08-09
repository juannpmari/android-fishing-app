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
import com.argentinapesca.argentinapesca.presentation.auth.AuthViewModel
import com.argentinapesca.argentinapesca.presentation.auth.AuthViewModelFactory
import com.argentinapesca.argentinapesca.repository.auth.AuthRepositoryImpl

class editProfileFragment : Fragment(R.layout.fragment_edit_profile) {

    val viewModel by viewModels<AuthViewModel> {
        AuthViewModelFactory(
            AuthRepositoryImpl(
                AuthDataSource()
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentEditProfileBinding.bind(view)

        binding.btnSave.setOnClickListener {
            val username = binding.editUsername.text.toString()
            val faceProfile = binding.editFace.text.toString()
            val celular = binding.editPhone.text.toString()
            viewModel.editUserInfo(username, faceProfile, celular).observe(viewLifecycleOwner) {
            }
            Toast.makeText(this.requireContext(), "Información actualizada", Toast.LENGTH_SHORT)
                .show()
            findNavController().popBackStack()
            //findNavController().popBackStack() //Si quiero volver al MainScreen
        }
    }
}