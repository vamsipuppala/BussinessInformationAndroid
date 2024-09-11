package com.example.yelp

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.ProgressDialog.show
import android.app.TimePickerDialog
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

import androidx.annotation.RequiresApi
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yelp.api.Details
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Calendar

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BusinessDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BusinessDetailsFragment : Fragment(), DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private lateinit var id: String
    private var param2: String? = null
    private lateinit var img: List<String>;
    var day = 0
    var month=0
    var year = 0
    var hour=0
    var minute = 0

    var savedday = 0
    var savedmonth=0
    var savedyear = 0
    var savedhour=0
    var savedminute = 0
    private fun getDateTimeCalendar()
    {
        val cal: Calendar=Calendar.getInstance()
        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONTH)
        year=cal.get(Calendar.YEAR)
        hour=cal.get(Calendar.HOUR)
        minute=cal.get(Calendar.MINUTE)
    }
//    private fun pickDate()
//    {
//        getDateTimeCalendar()
//        DatePickerDialog(requireContext(), requireContext(), year, month, day).show()
//    }
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
//        try {
//            var model_btm: Button = findViewById(R.id.reservenow)
//            model_btm.setOnClickListener {
//                val dialogBinding = layoutInflater.inflate(R.layout.modal, null)
//                val myDialog = Dialog(this)
//                myDialog.setContentView((dialogBinding))
//                myDialog.setCancelable(true)
//                myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//                myDialog.show()
//                val yebtn = dialogBinding.findViewById<Button>(R.id.button3)
//                yebtn.setOnClickListener {
//                    myDialog.dismiss()
//                }
//
//            }
//        }
//        catch(e:Exception)
//        {
//            Log.d("modal", ""+e)
//        }

//        var t:TextView=findViewById(R.id.textView2)
//        t.text= id.toString()



    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        id = this.arguments?.getString("id").toString()
        return inflater.inflate(R.layout.fragment_business_details, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("receivedid",""+id)
//        var id: String = "T1RfgUMYKW3HD55SEJILbQ"
        val response: Response<Details>
        lateinit var myAdapter: imageAdapter
        lateinit var linearlayoutManager: LinearLayoutManager

        var name_bus:String = ""
//        var x: TextView = view.findViewById(R.id.textView3)

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val gson = GsonBuilder()
                    .setLenient()
                    .create()
                val api: ApiRequests = Retrofit.Builder()
                    .baseUrl("https://vamsi-assignment-8.wn.r.appspot.com/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
                    .create(ApiRequests::class.java)
                val response: Response<Details> = api.getDetailInfo(id.toString()).awaitResponse()
                if (response.isSuccessful) {
                    val data: Details = response.body()!!

                    img=data.photos

                    Log.d("MainActivity3 hello", data.toString());

//                        supportActionBar?.setTitle(data.name)
                    withContext(Dispatchers.Main) {
//                        view.findViewById<TextView>(R.id.textView3).text = data.name
                        name_bus=data.name
                        if (data.url.isNotEmpty()) {
                            var yelpBusiness: TextView = view.findViewById(R.id.textView15)
                            yelpBusiness.text = resources.getString(R.string.link)

                            Log.d("inside url", "inside" + yelpBusiness.text)
                            yelpBusiness.setOnClickListener {
                                val openURL = Intent(Intent.ACTION_VIEW)
                                openURL.data = Uri.parse(data.url)
                                startActivity(openURL)
                            }
                        }
                        if (data.display_phone.isNotEmpty()) {
                            var phone_num: TextView = view.findViewById(R.id.textView10)
                            phone_num.text = data.display_phone
                        }
                        if (data.location.display_address.isNotEmpty()) {
                            var addr: String = "";
                            for (i in data.location.display_address) {
                                addr += i + " ";
                            }
                            var addr1: TextView = view.findViewById(R.id.textView6)
                            addr1.text = addr
                        }
                        if (data.categories.isNotEmpty()) {
                            var addr: String = ""
                            var spl: String = ""
                            for (i in data.categories) {
                                addr += spl + i.title;
                                spl = " | "
                            }
                            var addr1: TextView = view.findViewById(R.id.textView14)
                            addr1.text = addr
                        }

                        try{
                            if(data.price!=null && data.price.isNotEmpty())
                            {
                                var price: TextView=view.findViewById(R.id.textView7)
                                price.text=data.price
                            }
                            Log.d("status-er", "jjj")
                            if(data.hours.size>0 && data.hours.isNotEmpty())
                            {
                                if(data.hours[0].is_open_now)
                                {  var status: TextView=view.findViewById(R.id.textView11)
                                    status.text = Html.fromHtml("<span style=\"color:green;\" >Open Now</span>", Html.FROM_HTML_MODE_COMPACT)
                                }
                                else{
                                    var status: TextView=view.findViewById(R.id.textView11)
                                    status.text = Html.fromHtml("<span style=\"color:red;\" >Closed</span>", Html.FROM_HTML_MODE_COMPACT)
                                }
                            }
                            else {

                            }
//                            x.text=data.name
                        }
                        catch(e: Exception)
                        {
                            Log.d("status-er", "jjj"+data.price)
                        }

                        try {
                            val re: RecyclerView = view.findViewById(R.id.image_recycle)
                            linearlayoutManager= LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                            re.layoutManager = linearlayoutManager
                            myAdapter = imageAdapter(requireContext(), data.photos)
                            myAdapter.notifyDataSetChanged()
                            re.adapter = myAdapter
                        }
                        catch (e:Exception)
                        {
                            Log.d("msg890", ""+e)
                        }
                    }

                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Log.d("details url failed", "" + e)

                    return@withContext
                }
            }
        }
        try {

            var model_btm: Button = view.findViewById(R.id.reservenow)

            model_btm.setOnClickListener {

                val myDialog = Dialog(requireContext())

                val dialogBinding = layoutInflater.inflate(R.layout.modal, null)
                myDialog.setContentView((dialogBinding))
                myDialog.setCancelable(true)
                myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                myDialog.show()
                var x: TextView = dialogBinding.findViewById(R.id.textView3)
                x.text=name_bus


                val da= dialogBinding.findViewById<TextView>(R.id.editTextDate)
                da.setOnClickListener {
                    getDateTimeCalendar()
                    var xi = DatePickerDialog(requireContext(), this, year, month, day)

                    xi.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                    xi.setOnDismissListener{
                        da.text= savedmonth.toString()+"-"+savedday.toString()+"-"+savedyear.toString()
                    }
                    xi.show()


                }
//                da.text= savedmonth.toString()+"-"+savedday.toString()+"-"+savedyear.toString()
                val da1= dialogBinding.findViewById<TextView>(R.id.editTextTime)
                da1.setOnClickListener {
                    getDateTimeCalendar()
                    var yi=TimePickerDialog(requireContext(), this, hour,minute,false)

                    yi.setOnDismissListener{
                        da1.text= savedhour.toString()+":"+savedminute.toString()
                    }
                    yi.show()

                }
                val yebtn = dialogBinding.findViewById<TextView>(R.id.textView37)
                yebtn.setOnClickListener {
                    myDialog.dismiss()
//                    try {
//                        val re: RecyclerView = view.findViewById(R.id.image_recycle)
//                        linearlayoutManager= LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
//                        re.layoutManager = linearlayoutManager
//                        myAdapter = imageAdapter(requireContext(), data1.photos)
//                        myAdapter.notifyDataSetChanged()
//                        re.adapter = myAdapter
//                    }
//                    catch (e:Exception)
//                    {
//                        Log.d("msg890", ""+e)
//                    }

                }
                val sub = dialogBinding.findViewById<TextView>(R.id.textView38)
                sub.setOnClickListener {
                    val sub = dialogBinding.findViewById<TextView>(R.id.editTextTextPersonName4)
                    Log.d("ijsj777", " "+sub.text)

                    var regex: String =
                        dialogBinding.findViewById<EditText>(R.id.editTextTextPersonName4).text.toString()
                    myDialog.dismiss()


                        if (!regex.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(regex).matches()) {
                            if(savedday==0 && savedmonth==0 && savedyear==0)
                            {
                                val toast = Toast.makeText(requireContext(), "Date should not be empty",Toast.LENGTH_LONG)
                                toast.show()
                            }
                            else if(savedhour==0 && savedminute==0)
                            {
                                val toast = Toast.makeText(requireContext(), "Time should not be empty",Toast.LENGTH_LONG)
                                toast.show()
                            }
                            else if(savedhour<10 || savedhour>17 || (savedhour==17 && savedminute>0))
                            {
                                val toast = Toast.makeText(requireContext(), "Time should not be between 10AM AND 5PM",Toast.LENGTH_LONG)
                                toast.show()
                            }
                            //0 - email
                            //1-date
                            //2-time
                            //3-name
                            //4-id
                            else
                            {   Log.d("shared", regex.toString()+ " "+savedday.toString()+ "/"+savedyear.toString()+ "/"+savedmonth.toString()+ "  "+savedhour.toString()+":"+savedminute.toString()+" "+ name_bus.toString() )
                                var sharedpref = requireContext().getSharedPreferences("reservations", 0)
                                var editor = sharedpref.edit()
                                var dat = dialogBinding.findViewById<TextView>(R.id.editTextTextPersonName4).text.toString() + '$' +  dialogBinding.findViewById<TextView>(R.id.editTextDate).text.toString() + '$' + dialogBinding.findViewById<TextView>(R.id.editTextTime).text.toString() + '$' + name_bus + '$' + id
                                Log.d("dat", dat)
                                editor.putString(id, dat)
                                editor.commit()
                                val toast = Toast.makeText(requireContext(), "Reservation Booked",Toast.LENGTH_LONG)
                                toast.show()
                            }

                        } else {
                            Log.d("insid890e", "else inside empty")
                        val toast = Toast.makeText(requireContext(), "InValid Email Address",Toast.LENGTH_LONG)
                        toast.show()
                        }

                }
//                val subtn = dialogBinding.findViewById<TextView>(R.id.textView38)
//                subtn.setOnClickListener {
////                    var regex: String =
////                        dialogBinding.findViewById<EditText>(R.id.editTextTextPersonName4).text.toString()
//                    myDialog.dismiss()
//
////                        if (!regex.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(regex).matches()) {
////                            Log.d("inside", "inside empty")
////                            val toast = Toast.makeText(
////                                requireContext(),
////                                "invalid email address",
////                                Toast.LENGTH_LONG
////                            )
////                            toast.show()
////                        } else {
////                            Log.d("insid890e", "else inside empty")
//////                        val toast = Toast.makeText(requireContext(), "invalid email address",Toast.LENGTH_LONG)
//////                        toast.show()
////                        }
//
//
//                }


            }
        }
        catch(e:Exception)
        {
            Log.d("modal", ""+e)
        }
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BusinessDetailsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BusinessDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
        savedhour=p1
        savedminute=p2
    }

    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
        savedday=p3
        savedmonth=p2
        savedyear=p1
        Log.d("dates", p1.toString()+" "+p2.toString()+" "+p3.toString())
    }
}