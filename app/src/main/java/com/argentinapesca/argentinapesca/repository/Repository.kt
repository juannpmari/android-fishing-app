package com.argentinapesca.argentinapesca.repository

import com.argentinapesca.argentinapesca.data.model.Post
import com.argentinapesca.argentinapesca.data.remote.DataSource

interface Repository{
    suspend fun getPosts(): List<Post>
}