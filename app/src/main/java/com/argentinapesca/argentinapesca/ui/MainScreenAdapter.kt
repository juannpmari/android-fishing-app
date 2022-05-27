package com.argentinapesca.argentinapesca.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.argentinapesca.argentinapesca.R
import com.argentinapesca.argentinapesca.data.model.Post
import com.argentinapesca.argentinapesca.databinding.PostItemBinding

class MainScreenAdapter(
    private val postList:List<Post>,
    private val bindingInterface: RecyclerBindingInterface
    ):RecyclerView.Adapter<MainScreenAdapter.ViewHolder>() {


    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: Post, bindingInterface: RecyclerBindingInterface) =
            bindingInterface.bindData(item, view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding=LayoutInflater.from(parent.context).inflate(R.layout.post_item,parent,false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(postList[position], bindingInterface)
    }

    override fun getItemCount(): Int = postList.size
}

interface RecyclerBindingInterface{
    fun bindData(item: Post, view: View)
}