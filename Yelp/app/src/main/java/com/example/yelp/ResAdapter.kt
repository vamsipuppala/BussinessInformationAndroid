package com.example.yelp

import android.content.ClipData
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.yelp.databinding.Reserveditem1Binding

class ResAdapter(context: Context, val data: ArrayList<*>): RecyclerView.Adapter<ResAdapter.ResViewHolder>() {


    class ResViewHolder(val binding: Reserveditem1Binding): RecyclerView.ViewHolder(binding.root){
        fun bind(data: String, position: Int){
            //0 - email
            //1-date
            //2-time
            //3-name
            //4-id
            binding.resNo.text = (position+1).toString()
            binding.resEmail.text = data.split("$")[0]
            binding.resDate.text = data.split("$")[1]
            binding.resName.text = data.split("$")[3]
            binding.resTime.text = data.split("$")[2]

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResViewHolder {
      val binding = Reserveditem1Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ResViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ResViewHolder, position: Int) {
        holder.bind(data.get(position).toString(), position)
    }

    override fun getItemCount(): Int {
    return data.size
    }


}