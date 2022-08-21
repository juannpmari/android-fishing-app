package com.argentinapesca.argentinapesca.presentation.newPost

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.argentinapesca.argentinapesca.repository.newPost.newPostRepository
import kotlinx.coroutines.Dispatchers

class newPostViewModel(private val repo: newPostRepository) : ViewModel() {
    fun createNewPost(
        title: String,
        description: String,
        bitmap: List<Bitmap>,
        place: String,
        price: String,
        species: String
    ) = liveData(Dispatchers.IO) {
        emit(repo.createNewPost(title, description, bitmap, place, price, species))
    }
}

class newPostViewModelFactory(private val repo: newPostRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(newPostRepository::class.java).newInstance(repo)
    }
}

