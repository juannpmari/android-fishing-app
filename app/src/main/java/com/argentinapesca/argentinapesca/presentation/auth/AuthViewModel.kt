package com.argentinapesca.argentinapesca.presentation.auth

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.argentinapesca.argentinapesca.repository.auth.AuthRepository
import com.argentinapesca.argentinapesca.repository.home.Repository
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import kotlinx.coroutines.Dispatchers

class AuthViewModel(private val repo: AuthRepository) : ViewModel() {
    fun signUp(email: String, password: String, username:String,phone:String, face:String) = liveData(Dispatchers.IO) {
        try {
            emit(repo.signUp(email, password,username,phone, face))
        } catch (e: Exception) {
            Log.d("signup", "$e")
            emit(e)
        }
    }

    fun signIn(email: String, password: String) = liveData(Dispatchers.IO) {
        try {
            emit(repo.signIn(email, password))
        } catch (e: Exception) {
            emit(e)
        }
    }

    fun getUserInfo(poster:String) = liveData(Dispatchers.IO){
        emit(repo.getUserInfo(poster))
    }

}

class AuthViewModelFactory(private val repo: AuthRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(AuthRepository::class.java).newInstance(repo)
    }
}
