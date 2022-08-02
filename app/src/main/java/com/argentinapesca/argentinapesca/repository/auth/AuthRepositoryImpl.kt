package com.argentinapesca.argentinapesca.repository.auth

import com.argentinapesca.argentinapesca.data.model.UserData
import com.argentinapesca.argentinapesca.data.remote.auth.AuthDataSource
import com.google.firebase.auth.FirebaseUser

class AuthRepositoryImpl(private val authDataSource: AuthDataSource) : AuthRepository {
    override suspend fun signUp(email: String, password: String, username:String,phone:String, face:String): FirebaseUser? =
        authDataSource.signUp(email, password,username,phone, face)

    override suspend fun signIn(email: String, password: String): FirebaseUser? =
        authDataSource.signIn(email, password)

    override suspend fun getUserInfo(poster: String): UserData = authDataSource.getUserInfo(poster)
}