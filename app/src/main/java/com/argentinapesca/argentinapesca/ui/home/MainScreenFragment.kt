package com.argentinapesca.argentinapesca.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.RadioGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.argentinapesca.argentinapesca.R
import com.argentinapesca.argentinapesca.data.model.Post
import com.argentinapesca.argentinapesca.data.remote.home.DataSource
import com.argentinapesca.argentinapesca.databinding.FragmentMainScreenBinding
import com.argentinapesca.argentinapesca.databinding.PostItemBinding
import com.argentinapesca.argentinapesca.presentation.home.PostViewModel
import com.argentinapesca.argentinapesca.presentation.home.PostViewModelFactory
import com.argentinapesca.argentinapesca.repository.home.RepositoryImpl
import com.argentinapesca.argentinapesca.ui.MainScreenAdapter
import com.argentinapesca.argentinapesca.ui.RecyclerBindingInterface
import com.bumptech.glide.Glide
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class MainScreenFragment : Fragment(R.layout.fragment_main_screen),

    MainScreenAdapter.OnClickListener {
    private lateinit var adapter: MainScreenAdapter
    private lateinit var binding: FragmentMainScreenBinding
    private val viewModel by viewModels<PostViewModel> {
        PostViewModelFactory(
            RepositoryImpl(
                DataSource()
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val auth = Firebase.auth

        binding = FragmentMainScreenBinding.bind(view)

        if (auth.currentUser != null) {
            val name = auth.currentUser?.displayName.toString()
            binding.userName.text = name
        } else binding.userName.text = "Ingresar/Registrarse"

        val options = listOf("ubicación", "menor precio", "mayor precio", "especie")
        val spinnerAdapter =
            ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_item, options)
        binding.spinnerSort.adapter = spinnerAdapter

        /*var sortBy = ""
        binding.spinnerSort.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                sortBy=binding.spinnerSort.selectedItem.toString()
                //Log.d("spinner","$sortBy")
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }*/


        val bindingInterface = object : RecyclerBindingInterface {
            override fun bindData(item: Post, view: View) {
                val itemBinding = PostItemBinding.bind(view)
                itemBinding.txtTitle.text = item.title
                itemBinding.txtPlace.text = item.place
                if (item.image.size > 0) {
                    Glide.with(context!!).load(item.image[0]).into(itemBinding.imgPost)
                }
            }
        }

        binding.userName.setOnClickListener {
            if (auth.currentUser == null) {
                val action = MainScreenFragmentDirections.actionMainScreenFragmentToAuthFragment()
                findNavController().navigate(action)
            } else {
                val action =
                    MainScreenFragmentDirections.actionMainScreenFragmentToProfileFragment()
                findNavController().navigate(action)
            }
        }

        viewModel.fetchPost().observe(viewLifecycleOwner, Observer { list ->

            //var sortBy = ""
            var sortedList = listOf<Post>()
            when(binding.spinnerSort.selectedItem.toString()){
                "ubicación" -> {sortedList=list.sortedBy { it.place }}
                "menor precio" -> {sortedList=list.sortedBy { it.title }}
                //Acá agregar las otras opciones
            }
            adapter = MainScreenAdapter(sortedList, bindingInterface, this@MainScreenFragment)
            binding.rvMainScreen.adapter = adapter

            binding.spinnerSort.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    //sortBy=binding.spinnerSort.selectedItem.toString()
                    //Log.d("spinner","$sortBy")
                    when(binding.spinnerSort.selectedItem.toString()){
                        "ubicación" -> {sortedList=list.sortedBy { it.place }}
                        "menor precio" -> {
                            Log.d("spinner","Ordenar por titulo")
                            sortedList=list.sortedBy { it.title }}
                        //Acá agregar las otras opciones
                    }
                    if(binding.rbMine.isChecked){
                        if (auth.currentUser != null) {
                            adapter = MainScreenAdapter(
                                sortedList.filter { s -> s.poster == auth.currentUser?.uid },
                                bindingInterface,
                                this@MainScreenFragment
                            )
                            binding.rvMainScreen.adapter = adapter
                        }
                    } else {
                        adapter = MainScreenAdapter(sortedList, bindingInterface, this@MainScreenFragment)
                        binding.rvMainScreen.adapter = adapter
                    }
                    //adapter = MainScreenAdapter(sortedList, bindingInterface, this@MainScreenFragment)
                    //binding.rvMainScreen.adapter = adapter
                }
                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }




            //adapter = MainScreenAdapter(sortedList, bindingInterface, this@MainScreenFragment)
            //binding.rvMainScreen.adapter = adapter

            val listener = object : RadioGroup.OnCheckedChangeListener {
                override fun onCheckedChanged(p0: RadioGroup?, p1: Int) {

                    if (p1 == binding.rbMine.id) {
                        if (auth.currentUser != null) {
                            adapter = MainScreenAdapter(
                                sortedList.filter { s -> s.poster == auth.currentUser?.uid },
                                bindingInterface,
                                this@MainScreenFragment
                            )
                            binding.rvMainScreen.adapter = adapter
                        }
                    } else {
                        adapter = MainScreenAdapter(sortedList, bindingInterface, this@MainScreenFragment)
                        binding.rvMainScreen.adapter = adapter
                    }


                }
            }
            binding.rbPubli.setOnCheckedChangeListener(listener)
        })
    }

    /*fun sortList(list:List<Post>){
        when(binding.spinnerSort.selectedItem.toString()){
            "ubicación" -> {lista=list.sortedBy { it.place }}
            "menor precio" -> {list=list.sortedBy { it.title }}
            //Acá agregar las otras opciones
        }
    }*/

    override fun onClick(item: Post) {
        val action =
            com.argentinapesca.argentinapesca.ui.home.MainScreenFragmentDirections.actionMainScreenFragmentToPostFragment(
                item.title,
                item.image.toTypedArray(),
                item.description,
                item.place,
                item.poster,
                item.posterName
                //item.faceLink
            )
        findNavController().navigate(action)
    }

}