package com.argentinapesca.argentinapesca.data.remote.auth

import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class AuthDataSource {

    suspend fun signUp(email: String, password: String): FirebaseUser? {
        val auth = Firebase.auth
        auth.createUserWithEmailAndPassword(email, password).await()
        return auth.currentUser
    }

    suspend fun signIn(email: String, password: String): FirebaseUser? {
        val auth = Firebase.auth
        auth.signInWithEmailAndPassword(email, password).await()
        return auth.currentUser
    }
}