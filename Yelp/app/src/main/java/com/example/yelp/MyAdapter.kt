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
import com.bumptech.glide.Glide
//import androidx.recyclerview.widget.RecyclerView
import com.example.yelp.api.Businesse
import kotlin.math.round


class MyAdapter(val context: Context, val userList: List<Businesse>): RecyclerView.Adapter<MyAdapter.ViewHolder>() {

        class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
            var sno1: TextView
            var imag: ImageView
            var name1: TextView
            var rat: TextView
            var dis: TextView
            init{
                sno1=itemView.findViewById(R.id.business_name)
                imag=itemView.findViewById(R.id.img)
                name1=itemView.findViewById(R.id.name)
                rat = itemView.findViewById(R.id.rating)
                dis= itemView.findViewById(R.id.distance)
            }

        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_items, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.sno1.text=(position+1).toString()
        holder.dis.text= round(userList[position].distance*0.000621371).toInt().toString()
        holder.rat.text=userList[position].rating.toString()
        holder.name1.text=userList[position].name
        try {
            Glide.with(holder.itemView).load(userList[position].image_url).into(holder.imag)
//            holder.itemView
        }
        catch( e: Exception)
        {
            Log.d("glide", ""+e)
        }
        try {
            holder.itemView.setOnClickListener {
                val context = holder.itemView.context
                val intent = Intent(context, MainActivity2::class.java)
                intent.putExtra("id", userList[position].id)
                intent.putExtra("name", userList[position].name)
                intent.putExtra("url", userList[position].url)
                intent.putExtra("lat", userList[position].coordinates.latitude.toString())
              intent.putExtra("long", userList[position].coordinates.longitude.toString())
                Log.d("intent908", ""+userList[position].coordinates.latitude)
                context.startActivity(intent)
            }
        }
        catch( e: Exception) {
            Log.d("rec-lis", ""+e)
        }


    }

    override fun getItemCount(): Int {
        return userList.size
    }


}