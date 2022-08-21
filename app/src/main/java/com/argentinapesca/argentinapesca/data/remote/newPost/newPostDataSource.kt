package com.argentinapesca.argentinapesca.data.remote.newPost

import android.graphics.Bitmap
import android.util.Log
import com.argentinapesca.argentinapesca.data.model.Post
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream
import java.util.*
import java.util.stream.IntStream.range

class newPostDataSource {

    suspend fun createNewPost(
        title: String,
        description: String,
        bitmapList: List<Bitmap>,
        place: String,
        price: String,
        species: String
    ) {
        val user = Firebase.auth.currentUser
        val storage = Firebase.storage.reference

        var imgList = mutableListOf<String>()
        try {
            coroutineScope {
                for (bitmap in bitmapList) {
                    var downloadUrl: String
                    var randomName = UUID.randomUUID().toString()
                    var imageRef = storage.child("${user?.uid}/images/$randomName")
                    var baos = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                    async(Dispatchers.IO) {
                        downloadUrl = imageRef.putBytes(baos.toByteArray())
                            .await().storage.downloadUrl.await().toString()
                        imgList.add(downloadUrl)
                    }
                }
            }
        } catch (e: Exception) {
            Log.d("img", "$e")
        }

        val id: String = Firebase.firestore.collection("posts").document().id
        val new_post = Post(
            title,
            imgList,
            description,
            price,
            user?.uid.toString(),
            user?.displayName.toString(),
            place,
            id,
            species
        )
        Firebase.firestore.collection("posts").document(id).set(new_post).await()

        //Sin breakpoint --> se suben al revés
        //con breakpoint en val new_post... --> se guarda una lista vacía
        //con breakpoint en Firebase... --> se suben en el orden correcto

        //Al subir más de dos fotos se guardan solo dos (si pongo algún breakpoint no se guarda ninguna)
    }
}