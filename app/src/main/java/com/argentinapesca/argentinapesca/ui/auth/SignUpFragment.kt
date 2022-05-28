package com.argentinapesca.argentinapesca.ui.auth

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewTreeLifecycleOwner
import com.argentinapesca.argentinapesca.R
import com.argentinapesca.argentinapesca.data.remote.auth.AuthDataSource
import com.argentinapesca.argentinapesca.databinding.FragmentSignUpBinding
import com.argentinapesca.argentinapesca.presentation.auth.AuthViewModel
import com.argentinapesca.argentinapesca.presentation.auth.AuthViewModelFactory
import com.argentinapesca.argentinapesca.repository.auth.AuthRepositoryImpl

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

        binding.btnSignUp.setOnClickListener {
            email = binding.editEmail.text.toString()
            password = binding.editPassword.text.toString()
            viewModel.signUp(email, password).observe(viewLifecycleOwner, Observer {
                //Log.d("usuario","${it?.email}")
            })
        }


    }
}