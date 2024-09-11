package com.example.yelp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.yelp.api.Businesse

class PageAdapter(fm: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(fm, lifecycle) {





    private lateinit var  id: String;
    private lateinit var  lat: String;
   private lateinit var  long: String;

    fun setData(id:String, lat: String, long: String) {
        this.id=id
       this.lat=lat
       this.long=long
    }
    override fun getItemCount(): Int {
        return 3
    }
    override fun createFragment(position: Int): Fragment {
        Log.d("position", position.toString())
        return when(position){
            0->{
                var frag=BusinessDetailsFragment()
                var bundle= Bundle()
                bundle.putString("id", id)
                frag.arguments=bundle
                return frag
            }
            1->{
                var fragment = LocationFragment()
                var bundle= Bundle()
                bundle.putString("lat", lat)
                bundle.putString("lng", long)
                fragment.arguments = bundle
                return fragment
            }
            2->{
                var fragment = ReviewsFragment()
                var bundle= Bundle()
                bundle.putString("id", id)
                fragment.arguments = bundle
                return fragment
            }
            else->{
                return BusinessDetailsFragment()
            }
        }
    }
}