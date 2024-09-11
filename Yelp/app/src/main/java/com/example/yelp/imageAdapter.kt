package com.example.yelp

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
//import androidx.recyclerview.widget.RecyclerView
//import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
//import androidx.recyclerview.widget.RecyclerView
import com.example.yelp.api.Businesse
import com.example.yelp.api.Review
import com.example.yelp.api.Reviews
import kotlinx.coroutines.NonDisposableHandle.parent
import kotlin.math.round


class imageAdapter(val context: Context, val userList: List<String>): RecyclerView.Adapter<imageAdapter.ViewHolder>() {

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var imag: ImageView
        init{
            imag=itemView.findViewById(R.id.img_c)
        }

    }
    override fun onBindViewHolder(holder: imageAdapter.ViewHolder, position: Int) {

        Glide.with(holder.itemView).load(userList[position]).into(holder.imag)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): imageAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.carousal, parent, false)
        return ViewHolder(itemView)
    }


    override fun getItemCount(): Int {
        return userList.size
    }




}