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


class ItemAdapter(val context: Context, val userList: List<Review>): RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var sno1: TextView

        var name1: TextView
        var rat: TextView
        var dis: TextView
        init{
            sno1=itemView.findViewById(R.id.name)

            name1=itemView.findViewById(R.id.review)
            rat = itemView.findViewById(R.id.rating)
            dis= itemView.findViewById(R.id.date)
        }

    }
    override fun onBindViewHolder(holder: ItemAdapter.ViewHolder, position: Int) {
        holder.sno1.text=userList[position].user.name
        holder.name1.text= userList[position].text.toString()

        holder.dis.text=userList[position].time_created.split(" ")[0].toString()
        holder.rat.text= "Rating: "+userList[position].rating.toString()+"/5"


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.reviews, parent, false)
        return ViewHolder(itemView)
    }


    override fun getItemCount(): Int {
        return userList.size
    }




}