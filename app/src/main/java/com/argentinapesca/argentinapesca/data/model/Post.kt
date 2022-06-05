package com.argentinapesca.argentinapesca.data.model

data class Post(
    val title:String="",
    val image:List<String> = listOf(),
    val description:String="",
    val poster:String ="",
    val place:String="",
    val faceLink:String=""
)