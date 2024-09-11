package com.example.yelp

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.Insets.add
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.text.TextUtils.replace
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TableLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.annotation.RequiresApi
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.yelp.api.Details
import com.example.yelp.api.Reviews
import com.example.yelp.api.temp
import com.google.android.material.tabs.TabLayout
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory
import com.google.android.material.tabs.TabLayoutMediator
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView

class MainActivity2 : AppCompatActivity() {
    var url = ""
    var name111=""
    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("MissingInflatedId")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        val tab_names = arrayOf(
            "BUSINESS DETAILS",
            "MAP LOCATION",
            "REVIEWS"
        )

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)


        var intent = intent
        var id=intent.getStringExtra("id")
        var lat = intent.getStringExtra("lat")
        var long = intent.getStringExtra("long")
        Log.d("in8990",""+lat)
        var na = intent.getStringExtra("name")
        name111=na.toString()
        url = intent.getStringExtra("url").toString()
        supportActionBar?.title = na.toString()
            val viewPage: ViewPager2 = findViewById(R.id.viewpager)
            val tab: TabLayout = findViewById(R.id.tab)
            val adapt = PageAdapter(supportFragmentManager, lifecycle)
            adapt.setData(id.toString(), lat.toString(), long.toString())

            viewPage.adapter = adapt
            TabLayoutMediator(/* tabLayout = */ tab, /* viewPager = */ viewPage) { tab, position ->
                tab.text = tab_names[position]
            }.attach()



//        try {
//            var model_btm: Button = findViewById(R.id.reservenow)
//            model_btm.setOnClickListener {
////                val myDialog = Dialog(this)
////
////                val dialogBinding = layoutInflater.inflate(R.layout.modal, null)
////                myDialog.setContentView((dialogBinding))
////                myDialog.setCancelable(true)
////                myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
////                myDialog.show()
////                val yebtn = dialogBinding.findViewById<Button>(R.id.button3)
////                yebtn.setOnClickListener {
////                    myDialog.dismiss()
////                }
//
//            }
//        }
//        catch(e:Exception)
//        {
//            Log.d("modal", ""+e)
//        }

//        GlobalScope.launch(Dispatchers.IO) {
//            try {
//                val gson = GsonBuilder()
//                    .setLenient()
//                    .create()
//                val api: ApiRequests = Retrofit.Builder()
//                    .baseUrl("https://vamsi-assignment-8.wn.r.appspot.com/")
//                    .addConverterFactory(GsonConverterFactory.create(gson))
//                    .build()
//                    .create(ApiRequests::class.java)
//                val response: Response<Details> = api.getDetailInfo(id.toString()).awaitResponse()
//                if (response.isSuccessful) {
//                    val data: Details = response.body()!!
//                    Log.d("MainActivity3", data.toString());
//                    withContext(Dispatchers.Main) {
//                        supportActionBar?.setTitle(data.name)
////                        findViewById<TextView>(R.id.textView3).text=data.name
//
////                    if(data.url.isNotEmpty())
////                    {
////                        var yelpBusiness: TextView=findViewById(R.id.textView15)
////                        yelpBusiness.text="Business Link"
////
////                        yelpBusiness.setOnClickListener {
////                            val openURL = Intent(Intent.ACTION_VIEW)
////                            openURL.data = Uri.parse(data.url)
////                            startActivity(openURL)
////                        }
////                    }
////                        if(data.display_phone.isNotEmpty())
////                        {
////                            var phone_num: TextView=findViewById(R.id.textView10)
////                            phone_num.text=data.display_phone
////                        }
////                        if(data.location.display_address.isNotEmpty())
////                        { var addr:String="";
////                            for(i in data.location.display_address)
////                            {
////                                addr += i + " ";
////                            }
////                            var addr1: TextView=findViewById(R.id.textView6)
////                            addr1.text=addr
////                        }
////                        if(data.categories.isNotEmpty())
////                        { var addr:String=""
////                            var spl: String = ""
////                            for(i in data.categories)
////                            {
////                                addr += spl + i.title;
////                                spl= " | "
////                            }
////                            var addr1: TextView=findViewById(R.id.textView14)
////                            addr1.text=addr
////                        }
////
////                        try{
////                            if(data.price!=null && data.price.isNotEmpty())
////                            {
////                                var price: TextView=findViewById(R.id.textView7)
////                                price.text=data.price
////                            }
////                            Log.d("status-er", "jjj")
////                        if(data.hours.size>0 && data.hours.isNotEmpty())
////                        {
////                            if(data.hours[0].is_open_now)
////                            {  var status: TextView=findViewById(R.id.textView11)
////                                status.text = Html.fromHtml("<span style=\"color:green;\" >Open Now</span>", Html.FROM_HTML_MODE_COMPACT)
////                            }
////                            else{
////                                var status: TextView=findViewById(R.id.textView11)
////                                status.text = Html.fromHtml("<span style=\"color:red;\" >Closed</span>", Html.FROM_HTML_MODE_COMPACT)
////                            }
////                        }}
////                        catch(e: Exception)
////                        {
////                            Log.d("status-er", "jjj"+data.price)
////                        }
//
//
//
//
//                    }
//                }
//            }
//            catch(e: Exception){
//                withContext(Dispatchers.Main) {
//                    Log.d("details url failed", "" + e)
//
//                    return@withContext
//                }
//            }
//            try {
//                val gson = GsonBuilder()
//                    .setLenient()
//                    .create()
//                val api: ApiRequests = Retrofit.Builder()
//                    .baseUrl("https://vamsi-assignment-8.wn.r.appspot.com/")
//                    .addConverterFactory(GsonConverterFactory.create(gson))
//                    .build()
//                    .create(ApiRequests::class.java)
//                val response: Response<Reviews> = api.getReviewInfo(id.toString()+"/reviews").awaitResponse()
//                if (response.isSuccessful) {
//                    val data: Reviews = response.body()!!
//                    Log.d("MainActivity6", data.toString());
//
//
//                    withContext(Dispatchers.Main) {
////                        val re: RecyclerView =findViewById(R.id.review_recycle)
//
//                    }
//                }
//            }
//            catch(e: Exception){
//                withContext(Dispatchers.Main) {
//                    Log.d("reviews url failed", "" + e)
//
//                    return@withContext
//                }
//            }
////            val viewPage: ViewPager2 = findViewById(R.id.viewpager)
////            val tab: TabLayout=findViewById(R.id.tab)
////            val adapt = PageAdapter(supportFragmentManager, lifecycle)
////            viewPage.adapter = adapt
////            TabLayoutMediator(/* tabLayout = */ tab, /* viewPager = */ viewPage) { tab, position ->
////                tab.text = tab_names[position]
////            }.attach()
//
//        }



//        var t:TextView=findViewById(R.id.textView2)
//        t.text= id.toString()

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.ic_detail_toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.facebook -> {
                val openURL = Intent(Intent.ACTION_VIEW)
                openURL.data = Uri.parse("http://www.facebook.com/sharer.php?u="+url.toString())
                startActivity(openURL)
            }
            R.id.twitter -> {
                val openURL = Intent(Intent.ACTION_VIEW)
                openURL.data = Uri.parse("https://twitter.com/intent/tweet/?text=check"+ name111 +"on Yelp. &amp;url="+url)
                startActivity(openURL)
            }
        }
        return true
        return super.onOptionsItemSelected(item)
    }

}