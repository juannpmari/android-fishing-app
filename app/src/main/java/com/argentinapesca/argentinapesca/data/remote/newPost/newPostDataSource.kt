package com.argentinapesca.argentinapesca.data.remote.newPost

import android.graphics.Bitmap
import com.argentinapesca.argentinapesca.data.model.Post
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.util.*

class newPostDataSource {

    suspend fun createNewPost(title: String, image: List<String>, description: String) {
        val user = Firebase.auth.currentUser

        /*val storage = Firebase.storage.reference
        val randomName = UUID.randomUUID().toString()
        val imageRef = storage.child("${user?.uid}/images/$randomName")
        val baos = ByteArrayOutputStream()
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        var downloadUrl = ""
        withContext(Dispatchers.IO) {
            downloadUrl = imageRef.putBytes(baos.toByteArray()).await().storage.downloadUrl.await().toString()
        }*/



        val new_post = Post(title, image, description, user?.uid.toString())
        Firebase.firestore.collection("posts").document().set(new_post)
            .await()
    }
}