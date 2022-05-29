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
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AuthFragment : Fragment(R.layout.fragment_auth) {
    private lateinit var auth: FirebaseAuth
    val viewModel by viewModels<AuthViewModel> {
        AuthViewModelFactory(
            AuthRepositoryImpl(
                AuthDataSource()
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //requireActivity().findViewById<NavigationView>(R.id.navView).menu.getItem(R.id.proveedor).setTitle("Ya logueado")
        val binding = FragmentAuthBinding.bind((view))

        //if(auth.currentUser!=null) requireActivity().findViewById<NavigationView>(R.id.navView).menu.findItem(R.id.proveedor).setTitle("Cambiar de cuenta")
        //else requireActivity().findViewById<NavigationView>(R.id.navView).menu.findItem(R.id.proveedor).setTitle("Ingresar")

        binding.btnSignIn.setOnClickListener {
            val email = binding.editEmail.text.toString()
            val password = binding.editPassword.text.toString()
            viewModel.signIn(email, password).observe(viewLifecycleOwner, Observer {
                val action = AuthFragmentDirections.actionAuthFragmentToMainScreenFragment()
                findNavController().navigate(action)
            })
        }
        binding.txtLogOut.setOnClickListener{
            Firebase.auth.signOut()
            findNavController().popBackStack()
        }
        binding.txtRegister.setOnClickListener {
            findNavController().navigate(AuthFragmentDirections.actionAuthFragmentToSignUpFragment())
        }

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



