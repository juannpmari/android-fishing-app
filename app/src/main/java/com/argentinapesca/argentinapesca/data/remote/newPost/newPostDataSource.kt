package com.argentinapesca.argentinapesca.data.remote.newPost

import android.graphics.Bitmap
import android.util.Log
import com.argentinapesca.argentinapesca.data.model.Post
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.util.*

class newPostDataSource {

    suspend fun createNewPost(
        title: String,
        description: String,
        bitmapList: List<Bitmap>
    ) {
        val user = Firebase.auth.currentUser
        val storage = Firebase.storage.reference

        var downloadUrl: String
        var imgList = mutableListOf<String>()
        var bitmap=bitmapList[0] //Anda para una foto, habría que ver porqué con más no anda
        var randomName = UUID.randomUUID().toString()
        var imageRef = storage.child("${user?.uid}/images/$randomName")
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        withContext(Dispatchers.IO) {
            downloadUrl =
                imageRef.putBytes(baos.toByteArray()).await().storage.downloadUrl.await()
                    .toString()
            Log.d("img", "${downloadUrl}")
        }
        imgList.add(downloadUrl)

        val new_post = Post(title, imgList, description, user?.uid.toString())
        Firebase.firestore.collection("posts").document().set(new_post).await()
    }
}