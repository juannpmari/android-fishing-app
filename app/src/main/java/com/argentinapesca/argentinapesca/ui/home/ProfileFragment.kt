package com.argentinapesca.argentinapesca.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.argentinapesca.argentinapesca.R
import com.argentinapesca.argentinapesca.data.model.UserData
import com.argentinapesca.argentinapesca.data.remote.auth.AuthDataSource
import com.argentinapesca.argentinapesca.databinding.FragmentEditProfileBinding
import com.argentinapesca.argentinapesca.databinding.FragmentProfileBinding
import com.argentinapesca.argentinapesca.presentation.auth.AuthViewModel
import com.argentinapesca.argentinapesca.presentation.auth.AuthViewModelFactory
import com.argentinapesca.argentinapesca.repository.auth.AuthRepositoryImpl
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import com.google.firebase.ktx.Firebase

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    val viewModel by viewModels<AuthViewModel> {
        AuthViewModelFactory(
            AuthRepositoryImpl(
                AuthDataSource()
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentProfileBinding.bind(view)

        binding.txtLogOut.setOnClickListener {
            Firebase.auth.signOut()
            findNavController().popBackStack()
        }
        viewModel.getUserInfo(Firebase.auth.currentUser?.uid.toString()).observe(viewLifecycleOwner) { result ->
            when (result) {
                is UserData -> {
                    binding.txtName.text = Firebase.auth.currentUser?.displayName.toString()
                    binding.txtEmail.text = Firebase.auth.currentUser?.email.toString()
                    binding.txtFaceProfile.text = result.faceProfile
                    binding.txtCellphone.text = result.celular
                }
            }
        }

        binding.btnEdit.setOnClickListener{
            findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToEditProfileFragment())
        }

    }
}