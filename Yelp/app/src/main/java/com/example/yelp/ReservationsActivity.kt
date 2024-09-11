package com.example.yelp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ReservationsActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservations)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        //0 - email
        //1-date
        //2-time
        //3-name
        //4-id
        findViewById<TextView>(R.id.nobookings).visibility = View.INVISIBLE
        var sharedPref = this@ReservationsActivity.getSharedPreferences("reservations", 0)
        var data = ArrayList(sharedPref.all.values)
        if(data.isEmpty()){
            findViewById<TextView>(R.id.nobookings).visibility = View.VISIBLE
        }
        Log.d("data", data.toString())
        var adapter = ResAdapter(this@ReservationsActivity, data)
        findViewById<RecyclerView>(R.id.recyclerres).adapter = adapter







    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}