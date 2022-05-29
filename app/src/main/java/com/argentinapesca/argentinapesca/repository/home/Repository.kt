package com.argentinapesca.argentinapesca.repository.home

import com.argentinapesca.argentinapesca.data.model.Post

interface Repository{
    suspend fun getPosts(): List<Post>
}