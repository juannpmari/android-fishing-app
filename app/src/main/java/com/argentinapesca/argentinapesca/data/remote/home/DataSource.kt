package com.argentinapesca.argentinapesca.data.remote.home

import com.argentinapesca.argentinapesca.data.model.Post
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class DataSource {

    suspend fun getPosts(): List<Post> {
        /**/
        val postList = mutableListOf<Post>()
        val querySnapshot = FirebaseFirestore.getInstance().collection("posts").get().await()
        for (post in querySnapshot.documents) {
            post.toObject(Post::class.java)?.let { fbpost -> //Transformo el document en un Post
                postList.add(fbpost)
            }
        }
        return postList
    }

    /*
    //Esto NO anda
    val postList = mutableListOf<Post>()
   FirebaseFirestore.getInstance().collection("posts").get().addOnSuccessListener { querySnapshot->
        for (post in querySnapshot.documents) {
            post.toObject(Post::class.java)?.let {
                postList.add(it)
                //Log.d("posteos", "${it.title}")
            }
        }
    }
    return postList
}*/


}
/*
val new_post=post("Pesca de trucha")
db.collection("posts").document("BmMGdgCsB9J35EWYsnHw").set(new_post)
.addOnSuccessListener { Log.d("Set", "DocumentSnapshot successfully written!") }
.addOnFailureListener { e -> Log.w("Set", "Error writing document", e) }
*/


