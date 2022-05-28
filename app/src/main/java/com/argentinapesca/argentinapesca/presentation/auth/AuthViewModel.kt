package com.argentinapesca.argentinapesca.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.argentinapesca.argentinapesca.repository.auth.AuthRepository
import com.argentinapesca.argentinapesca.repository.home.Repository
import kotlinx.coroutines.Dispatchers

class AuthViewModel(private val repo: AuthRepository) : ViewModel() {
    fun signUp(email:String, password:String) = liveData(Dispatchers.IO) {
        emit(repo.signUp(email,password))
    }
}

class AuthViewModelFactory(private val repo: AuthRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(AuthRepository::class.java).newInstance(repo)
    }
}
