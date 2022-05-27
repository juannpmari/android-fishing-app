package com.argentinapesca.argentinapesca

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.argentinapesca.argentinapesca.data.model.Post
import com.argentinapesca.argentinapesca.data.remote.DataSource
import com.argentinapesca.argentinapesca.presentation.PostViewModel
import com.argentinapesca.argentinapesca.presentation.PostViewModelFactory
import androidx.fragment.app.viewModels
import com.google.firebase.firestore.FirebaseFirestore
//import kotlinx.coroutines.*
//import java.util.*
import androidx.lifecycle.Observer
import com.argentinapesca.argentinapesca.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
}



