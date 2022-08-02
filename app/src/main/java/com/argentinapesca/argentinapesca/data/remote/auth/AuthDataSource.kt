package com.argentinapesca.argentinapesca.data.remote.auth

import android.util.Log
import android.widget.Toast
import com.argentinapesca.argentinapesca.data.model.Post
import com.argentinapesca.argentinapesca.data.model.UserData
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.lang.NullPointerException
import java.sql.Types.NULL

class AuthDataSource {

    suspend fun signUp(email: String, password: String, username:String,phone:String, face:String): FirebaseUser? {
        val auth = Firebase.auth
        auth.createUserWithEmailAndPassword(email, password).await()
        auth.currentUser?.updateProfile(userProfileChangeRequest { displayName = username })
        val new_user = UserData(auth.uid.toString(),face,phone)//, faceLink)
        Firebase.firestore.collection("users").document().set(new_user).await()
        return auth.currentUser
    }

    suspend fun signIn(email: String, password: String): FirebaseUser? {
        val auth = Firebase.auth
        auth.signInWithEmailAndPassword(email, password).await()
        return auth.currentUser
    }

    suspend fun getUserInfo(poster: String): UserData {
        //val userUID = Firebase.auth.currentUser?.uid
        val querySnapshot = FirebaseFirestore.getInstance().collection("users").get().await()
        var curUser = UserData("null", "null","null")

        for (user in querySnapshot.documents) {
            user.toObject(UserData::class.java)?.let {
                if (it.uid == poster) curUser = it
            }
        }
        return curUser
    }
}