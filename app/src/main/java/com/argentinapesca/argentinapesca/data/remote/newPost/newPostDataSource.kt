package com.argentinapesca.argentinapesca.data.remote.newPost

import android.graphics.Bitmap
import android.util.Log
import android.widget.Toast
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
import kotlin.coroutines.coroutineContext
import kotlin.properties.Delegates

class newPostDataSource {

    suspend fun createNewPost(
        title: String,
        description: String,
        bitmapList: List<Bitmap>,
        place: String,
        price: String,
        species: String
    ) {

        //for (img in bitmapList) Log.d("img","data source" + img.toString())

        val user = Firebase.auth.currentUser
        val storage = Firebase.storage.reference
        var cont = 0

        var imgList = mutableListOf<String>()
        try {
            coroutineScope {
                for (bitmap in bitmapList) {
                    var randomName = UUID.randomUUID().toString()
                    var imageRef = storage.child("${user?.uid}/images/$randomName")
                    var baos = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                    imageRef.putBytes(baos.toByteArray()).addOnSuccessListener {
                        it.storage.downloadUrl.addOnSuccessListener { uri ->
                            imgList.add(uri.toString())
                            cont++
                            //Log.d("img", imgList.last().toString()+"cont="+cont)
                            if (cont == bitmapList.lastIndex + 1) setPost(
                                title,
                                description,
                                place,
                                price,
                                species,
                                imgList
                            )
                        }
                    }
                }
            }
        } catch (e: Exception) {
            Log.d("img", "externo $e")
        }

/*val id: String = Firebase.firestore.collection("posts").document().id
 val new_post Post(
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

  //Esto podría borrarse
        */
    }

    private fun setPost(
        title: String,
        description: String,
        place: String,
        price: String,
        species: String,
        imgList: List<String>
    ) {
        val user = Firebase.auth.currentUser
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
        Firebase.firestore.collection("posts").document(id).set(new_post)
    }

}