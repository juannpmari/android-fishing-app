package com.argentinapesca.argentinapesca.data.model

data class Post(
    val title: String = "",
    val image: List<String> = listOf(),
    val description: String = "",
    val price: String = "",
    val poster: String = "",
    val posterName: String = "",
    val place: String = "",
    val id: String = "",
)