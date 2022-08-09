package com.argentinapesca.argentinapesca.ui.auth

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.argentinapesca.argentinapesca.R
import com.argentinapesca.argentinapesca.data.remote.auth.AuthDataSource
import com.argentinapesca.argentinapesca.databinding.FragmentAuthBinding
import com.argentinapesca.argentinapesca.presentation.auth.AuthViewModel
import com.argentinapesca.argentinapesca.presentation.auth.AuthViewModelFactory
import com.argentinapesca.argentinapesca.repository.auth.AuthRepositoryImpl
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.protobuf.Empty
import java.lang.IllegalArgumentException

class AuthFragment : Fragment(R.layout.fragment_auth) {
    //private lateinit var auth: FirebaseAuth
    private lateinit var binding: FragmentAuthBinding

    val viewModel by viewModels<AuthViewModel> {
        AuthViewModelFactory(
            AuthRepositoryImpl(
                AuthDataSource()
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAuthBinding.bind((view))


        binding.btnSignIn.setOnClickListener {
            val email = binding.editEmail.text.toString()
            val password = binding.editPassword.text.toString()
            validateCredentials(email, password)
            viewModel.signIn(email, password).observe(viewLifecycleOwner, Observer { result ->
                when (result) {
                    is FirebaseUser -> {
                        val action = AuthFragmentDirections.actionAuthFragmentToMainScreenFragment()
                        findNavController().navigate(action)
                    }
                    is FirebaseAuthInvalidCredentialsException -> {
                        Toast.makeText(
                            this.requireContext(),
                            "Ingrese un email válido",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    is FirebaseAuthInvalidUserException -> {
                        Toast.makeText(
                            this.requireContext(),
                            "No se encontró el usuario",
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
                /*if (it is FirebaseUser) {
                    val action = AuthFragmentDirections.actionAuthFragmentToMainScreenFragment()
                    findNavController().navigate(action)
                } else Toast.makeText(
                    this.requireContext(),
                    "Email o contraseña son inválidos",
                    Toast.LENGTH_SHORT
                ).show()*/
            })
        }
        binding.txtRegister.setOnClickListener {
            findNavController().navigate(AuthFragmentDirections.actionAuthFragmentToSignUpFragment())
        }

    }

    private fun validateCredentials(email: String, password: String) {
        if (email.isEmpty()) {
            binding.editEmail.error = "Ingrese un email"
        }
        if (password.isEmpty()) {
            binding.editPassword.error = "Ingrese una contraseña"
        }
        //Acá podría tmb validar si tienen formato correcto, etc.
    }

}


// Initialize Firebase Auth
// auth = Firebase.auth

/* //Crear nuevo usuario
 auth.createUserWithEmailAndPassword("jpabb0705@gmail.com", "liverpool")
     .addOnCompleteListener{ task ->
         if (task.isSuccessful) {
             // Sign in success, update UI with the signed-in user's information
             Log.d("TAG", "createUserWithEmail:success")
             val user = auth.currentUser
             //updateUI(user)
         } else {
             // If sign in fails, display a message to the user.
             Log.w("TAG", "createUserWithEmail:failure", task.exception)
             Toast.makeText(context, "Authentication failed.",
                 Toast.LENGTH_SHORT).show()
             //updateUI(null)
         }
     }
*/
/*  //Sign-in del usuario
auth.signInWithEmailAndPassword("jpabb0705@gmail.com", "liverpool")
.addOnCompleteListener{ task ->
   if (task.isSuccessful) {
       // Sign in success, update UI with the signed-in user's information

       val user = auth.currentUser
       Log.d("TAG", "${user?.email}")
       Log.d("TAG", "${auth.currentUser?.uid}")
       //updateUI(user)
   } else {
       // If sign in fails, display a message to the user.
       Log.w("TAG", "signInWithEmail:failure", task.exception)
       Toast.makeText(context, "Authentication failed.",
           Toast.LENGTH_SHORT).show()
       //updateUI(null)
   }
}*/



