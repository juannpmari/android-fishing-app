package com.argentinapesca.argentinapesca.repository.newPost

import android.graphics.Bitmap

interface newPostRepository {
    suspend fun createNewPost(title: String,description: String, bitmap: List<Bitmap>,place:String, price:String,species:String)
}