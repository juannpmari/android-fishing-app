package com.argentinapesca.argentinapesca.repository.newPost

import com.argentinapesca.argentinapesca.data.remote.newPost.newPostDataSource

class newPostRepositoryImpl(private val newPostDataSource: newPostDataSource) : newPostRepository {
    override suspend fun createNewPost(title: String, image: String, description: String) =
        newPostDataSource.createNewPost(title, image, description)

}