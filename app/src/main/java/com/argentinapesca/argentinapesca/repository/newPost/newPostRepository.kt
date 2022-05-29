package com.argentinapesca.argentinapesca.repository.newPost

interface newPostRepository {
    suspend fun createNewPost(title: String, image: String, description: String)
}