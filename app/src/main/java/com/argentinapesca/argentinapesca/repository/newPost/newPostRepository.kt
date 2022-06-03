package com.argentinapesca.argentinapesca.repository.newPost

import android.graphics.Bitmap

interface newPostRepository {
    suspend fun createNewPost(title: String, image: List<String>, description: String, bitmap: Bitmap)
}