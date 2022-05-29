package com.argentinapesca.argentinapesca.data.remote.newPost

import com.argentinapesca.argentinapesca.data.model.Post
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class newPostDataSource {

    suspend fun createNewPost(title: String, image: String, description: String) {
        val user = Firebase.auth.currentUser
        val new_post = Post(title, image, description, user?.uid.toString())
        Firebase.firestore.collection("posts").document().set(new_post)
            .await()
    }
}