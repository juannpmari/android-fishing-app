package com.argentinapesca.argentinapesca.repository.newPost

import android.graphics.Bitmap
import com.argentinapesca.argentinapesca.data.remote.newPost.newPostDataSource

class newPostRepositoryImpl(private val newPostDataSource: newPostDataSource) : newPostRepository {
    override suspend fun createNewPost(title: String, image: List<String>, description: String, bitmap: Bitmap) =
        newPostDataSource.createNewPost(title, image, description,bitmap)

}