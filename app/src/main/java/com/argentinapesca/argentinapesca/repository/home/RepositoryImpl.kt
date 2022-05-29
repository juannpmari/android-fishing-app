package com.argentinapesca.argentinapesca.repository.home

import com.argentinapesca.argentinapesca.data.remote.home.DataSource

class RepositoryImpl(private val dataSource: DataSource): Repository {
    override suspend fun getPosts() = dataSource.getPosts()
}