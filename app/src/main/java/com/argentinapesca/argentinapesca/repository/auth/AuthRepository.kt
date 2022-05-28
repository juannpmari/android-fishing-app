package com.argentinapesca.argentinapesca.repository.auth

import com.argentinapesca.argentinapesca.data.remote.auth.AuthDataSource
import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    suspend fun signUp(email:String, password:String) : FirebaseUser?
}