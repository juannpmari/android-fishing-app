package com.argentinapesca.argentinapesca.repository.auth

import com.argentinapesca.argentinapesca.data.model.UserData
import com.argentinapesca.argentinapesca.data.remote.auth.AuthDataSource
import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    suspend fun signUp(
        email: String,
        password: String,
        username: String,
        phone: String,
        face: String
    ): FirebaseUser?

    suspend fun signIn(email: String, password: String): FirebaseUser?
    suspend fun getUserInfo(poster: String): UserData
    suspend fun editUserInfo(
        username: String = "",
        faceProfile: String = "",
        celular: String = ""
    )
}