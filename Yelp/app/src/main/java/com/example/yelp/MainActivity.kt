package com.example.yelp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.Html
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yelp.api.*
import com.google.gson.GsonBuilder
import kotlinx.coroutines.*
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory


const val base_url = "https://ipinfo.io"
const val buss_url = "https://vamsi-assignment-8.wn.r.appspot.com"
class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    @RequiresApi(Build.VERSION_CODES.N)
    lateinit var myAdapter: MyAdapter
    var job: Job? = null
    lateinit var linearlayoutManager: LinearLayoutManager
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val list=arrayOf("Default", "Arts and Entertainment", "Health and Medical", "Hotels and Travel", "Food", "Professional Services")
        val keyword:AutoCompleteTextView=findViewById(R.id.editTextTextPersonName)
        val dis:EditText=findViewById(R.id.editTextTextPersonName2)
        val head:TextView=findViewById(R.id.heading)
        val category:TextView=findViewById(R.id.textView)
        val spinner=findViewById<Spinner>(R.id.spinner)
        val location:EditText=findViewById(R.id.editTextTextPersonName3)
        val check:CheckBox=findViewById(R.id.checkBox)
        val button:Button=findViewById(R.id.button)
        val button2:Button=findViewById(R.id.button2)
        val re:RecyclerView=findViewById(R.id.recycle)
        re.setHasFixedSize(true)
        linearlayoutManager= LinearLayoutManager(this)
//        val re:RecyclerView=findViewById(R.id.recycle)
//        re.setHasFixedSize(true)
//        linearlayoutManager= LinearLayoutManager(this)
//        re.layoutManager=linearlayoutManager
//        linearlayoutManager= LinearLayoutManager(this)
//        recyclerView.layoutManager=linearlayoutManager



        location.hint = Html.fromHtml("<label>Location </label> <span style=\"color:red;\" >*</span>", Html.FROM_HTML_MODE_COMPACT)
        head.text = Html.fromHtml("<h2>Business Search</h2>", Html.FROM_HTML_MODE_COMPACT)
        keyword.hint = Html.fromHtml("<label>KeyWord </label> <span style=\"color:red;\" >*</span>", Html.FROM_HTML_MODE_COMPACT)
        dis.hint = Html.fromHtml("<label>Distance </label>", Html.FROM_HTML_MODE_COMPACT)
        category.text= Html.fromHtml("<span>Category </span> <span style=\"color:red;\" >*</span>", Html.FROM_HTML_MODE_COMPACT)
        val arrayAdapter=ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, list)
        spinner.adapter=arrayAdapter

        button2.setOnClickListener {
            dis.setText("")
            keyword.setText("")
            location.visibility=View.VISIBLE
            location.setText("")
            check.isChecked=false
            spinner.setSelection(0)
            dis.hint = Html.fromHtml("<label>Distance </label>", Html.FROM_HTML_MODE_COMPACT)
            keyword.hint = Html.fromHtml("<label>KeyWord </label> <span style=\"color:red;\" >*</span>", Html.FROM_HTML_MODE_COMPACT)
            location.hint = Html.fromHtml("<label>Location </label> <span style=\"color:red;\" >*</span>", Html.FROM_HTML_MODE_COMPACT)
            re.visibility=View.INVISIBLE



        }
        check.setOnCheckedChangeListener { CompoundButton, b ->
            if(check.isChecked)
            {
                location.text= Html.fromHtml("", Html.FROM_HTML_MODE_COMPACT) as Editable?
                location.visibility = View.INVISIBLE
            }
            else{
                location.visibility = View.VISIBLE
            }
        }
        val retrofit = Retrofit.Builder()
            .baseUrl(buss_url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiRequests::class.java)
        keyword.addTextChangedListener {
                    job?.cancel()
                    job = MainScope().launch {
                        delay(200L)
                        try {
                            var res: Response<List<String>> =
                                retrofit.getAutoResults(it.toString()).awaitResponse()
                            if (res.isSuccessful) {
                                keyword.setAdapter(
                                    ArrayAdapter<String>(
                                        this@MainActivity,
                                        android.R.layout.simple_dropdown_item_1line,
                                        res.body() as MutableList<String>
                                    )
                                )
                                keyword.showDropDown()
                            }
                        }
                        catch (
                            e: Exception
                        )
                        {
                            Log.d("error", e.toString())
                        }
                    }

        }

        keyword.setOnItemClickListener(object : AdapterView.OnItemClickListener {
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                job?.cancel()
            }

        })

        button.setOnClickListener {
            val keyWord = keyword.text.toString().trim()
            val dist = dis.text.toString().trim()
            var loc = location.text.toString().trim()
            var cate = spinner.selectedItem.toString().trim()
            val checked = check.isChecked
            var num=0
            var lat=-1.0
            var long=-1.9

            try {
                num = dist.toInt() * 1609
                if(num<0)
                {
                    dis.error = "This "
                    return@setOnClickListener
                }
            }
            catch (e: Exception)
            {
                dis.error = "This field is required"
                return@setOnClickListener
            }
            if (keyWord.isEmpty()) {
                keyword.error = "This field is required"
                return@setOnClickListener
            }
            else if(dist.isEmpty())
            {
                dis.error = "This field is required"
                return@setOnClickListener
            }
            else if(!checked && loc.isEmpty())
            {
                location.error = "This field is required"
                return@setOnClickListener
            }
            else{


                val api: ApiRequests = Retrofit.Builder()
                    .baseUrl(base_url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ApiRequests::class.java)


                GlobalScope.launch(Dispatchers.IO) {

                    if (checked) {
                        try {
                            val response: Response<ipinfo> = api.getIpInfo().awaitResponse()
                            if (response.isSuccessful) {
                                val data: ipinfo = response.body()!!
                                Log.d("MainActivity", data.loc);
                                lat=data.loc.split(",")[0].toDouble()
                                long=data.loc.split(",")[1].toDouble()
                                withContext(Dispatchers.Main) {

                                }
                            }
                        } catch (e: Exception) {
                            withContext(Dispatchers.Main) {
                                Log.d("auto-detect failed", "" + e)

                                return@withContext
                            }

                        }
                    }
                    else{
                        val loc = location.text.toString().trim()
                        try {
                            val gson = GsonBuilder()
                                .setLenient()
                                .create()
                            val api: ApiRequests = Retrofit.Builder()
                                .baseUrl("https://vamsi-assignment-8.wn.r.appspot.com/")
                                .addConverterFactory(GsonConverterFactory.create(gson))
                                .build()
                                .create(ApiRequests::class.java)

                            val response: Response<temp> = api.getLocInfo(loc).awaitResponse()
                            if (response.isSuccessful) {
                                val data: temp = response.body()!!
                                Log.d("MainActivity1", data[0].toString());
                                lat=data[0]
                                long=data[1]
                                withContext(Dispatchers.Main) {

                                }
                            }
                        } catch (e: Exception) {
                            withContext(Dispatchers.Main) {
                                Log.d("Geo-location-failed", "" + e)

                                return@withContext
                            }
                        }

                    }
                    if(cate == "Arts and Entertainment")
                    {
                        cate = "arts"
                    }
                    else if(cate == "Health and Medical")
                    {
                        cate = "health";
                    }
                    else if(cate == "Hotels and Travel")
                    {
                        cate = "hotelstravel"
                    }
                    else if(cate== "Food")
                    {
                        cate = "food"
                    }
                    else if(cate== "Professional Services")
                    {
                        cate = "professional"
                    }
                    else{
                        Log.d("entered-all","here"+cate)
                        cate = "all"
                    }
                    try {
                        val gson = GsonBuilder()
                            .setLenient()
                            .create()
                        val api: ApiRequests = Retrofit.Builder()
                            .baseUrl("https://vamsi-assignment-8.wn.r.appspot.com/")
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .build()
                            .create(ApiRequests::class.java)
                        val response: Response<table> =
                            api.getTableInfo(keyWord, cate, num, lat, long).awaitResponse()
                        if (response.isSuccessful) {
                            val data:table = response.body()!!
                            Log.d("MainActivity1", data.toString());
                            val d_data: List<Businesse> =data.businesses
                            Log.d("business_data",d_data.toString())
                            withContext(Dispatchers.Main) {



                                try {
                                    re.visibility=View.VISIBLE
//
                                    re.layoutManager = linearlayoutManager
                                    myAdapter = MyAdapter(this@MainActivity, d_data)
                                    myAdapter.notifyDataSetChanged()
                                    re.adapter = myAdapter

//                                recyclerView.notifyDataSetChanged()
                                    // Use this setting to improve performance if you know that changes
                                    // in content do not change the layout size of the RecyclerView
//                                val recyclerView = findViewById<RecyclerView>(R.id.recycle)
//                                recyclerView.adapter = ItemAdapter(this, d_data)
//                                recyclerView.setHasFixedSize(true)
                                } catch (e: Exception) {

                                    Log.d("recycler-error", "here " + e)
                                }
                            }

                        }
                    }
                    catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            Log.d("Backend table failed", "" + e)

                            return@withContext
                        }
                    }
                }
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.ic_reserve_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
       when(item.itemId){
           R.id.reservations -> {
               val intent = Intent(this@MainActivity, ReservationsActivity::class.java)
               this@MainActivity.startActivity(intent)
           }
       }
        return true
    }

}