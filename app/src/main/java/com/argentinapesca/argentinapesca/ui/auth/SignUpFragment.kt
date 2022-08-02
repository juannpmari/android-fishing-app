package com.argentinapesca.argentinapesca.ui.auth

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewTreeLifecycleOwner
import androidx.navigation.fragment.findNavController
import com.argentinapesca.argentinapesca.R
import com.argentinapesca.argentinapesca.data.remote.auth.AuthDataSource
import com.argentinapesca.argentinapesca.databinding.FragmentSignUpBinding
import com.argentinapesca.argentinapesca.presentation.auth.AuthViewModel
import com.argentinapesca.argentinapesca.presentation.auth.AuthViewModelFactory
import com.argentinapesca.argentinapesca.repository.auth.AuthRepositoryImpl
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.ktx.Firebase
import java.lang.IllegalArgumentException

class SignUpFragment : Fragment(R.layout.fragment_sign_up) {
    private val viewModel by viewModels<AuthViewModel> {
        AuthViewModelFactory(
            AuthRepositoryImpl(
                AuthDataSource()
            )
        )
    }
    private lateinit var binding: FragmentSignUpBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSignUpBinding.bind(view)
        var email = ""
        var password = ""
        var username = ""
        var phone = ""
        var face = ""

        binding.btnSignUp.setOnClickListener {
            email = binding.editEmail.text.toString()
            password = binding.editPassword.text.toString()
            username = binding.editUsername.text.toString()
            phone = binding.editPhone.text.toString()
            face = binding.editFace.text.toString()
            validateCredentials(email, password)
            viewModel.signUp(email, password, username,phone, face)
                .observe(viewLifecycleOwner, Observer { result ->
                    when (result) {
                        is FirebaseUser -> {
                            findNavController().navigate(R.id.mainScreenFragment)
                        }
                        is FirebaseAuthInvalidCredentialsException -> {
                            Toast.makeText(
                                this.requireContext(),
                                "Ingrese un email válido",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        is FirebaseAuthWeakPasswordException -> {
                            Toast.makeText(
                                this.requireContext(),
                                "La contraseña debe contener al menos 6 caracteres",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        is FirebaseAuthUserCollisionException -> {
                            Toast.makeText(
                                this.requireContext(),
                                "Ya existe un usuario registrado con este email",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        is IllegalArgumentException -> {
                        }
                        else -> {
                            Toast.makeText(
                                this.requireContext(),
                                "Ocurrió un error",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                })
        }
    }

    private fun validateCredentials(email: String, password: String) {
        if (email.isEmpty()) {
            binding.editEmail.error = "Ingrese un email"
        }
        if (password.isEmpty()) {
            binding.editPassword.error = "Ingrese una contraseña"
        }
    }
}