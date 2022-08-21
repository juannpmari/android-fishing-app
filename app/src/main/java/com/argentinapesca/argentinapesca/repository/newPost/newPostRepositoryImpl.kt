package com.argentinapesca.argentinapesca.repository.newPost

import android.graphics.Bitmap
import com.argentinapesca.argentinapesca.data.remote.newPost.newPostDataSource

class newPostRepositoryImpl(private val newPostDataSource: newPostDataSource) : newPostRepository {
    override suspend fun createNewPost(title: String, description: String, bitmap: List<Bitmap>,place:String,price:String, species:String) =
        newPostDataSource.createNewPost(title, description,bitmap,place,price,species)

}