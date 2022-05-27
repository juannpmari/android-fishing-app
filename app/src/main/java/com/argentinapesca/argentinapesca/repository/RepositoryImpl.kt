package com.argentinapesca.argentinapesca.repository

import com.argentinapesca.argentinapesca.data.remote.DataSource

class RepositoryImpl(private val dataSource: DataSource):Repository {
    override suspend fun getPosts() = dataSource.getPosts()
}