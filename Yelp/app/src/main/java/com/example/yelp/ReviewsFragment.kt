package com.example.yelp

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yelp.api.Businesse
import com.example.yelp.api.Review
import com.example.yelp.api.Reviews
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ReviewsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ReviewsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private lateinit var id: String
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        id = this.arguments?.getString("id").toString()
        return inflater.inflate(R.layout.fragment_reviews, container, false)
    }
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lateinit var myAdapter: ItemAdapter
        lateinit var linearlayoutManager: LinearLayoutManager
        GlobalScope.launch(Dispatchers.IO) {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        val api: ApiRequests = Retrofit.Builder()
            .baseUrl("https://vamsi-assignment-8.wn.r.appspot.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiRequests::class.java)
        val response: Response<Reviews> = api.getReviewInfo(id.toString()+"/reviews").awaitResponse()
        if (response.isSuccessful) {
            val data: Reviews = response.body()!!
            val d_data: List<Review> =data.reviews
            Log.d("MainActivity6", data.toString());
            withContext(Dispatchers.Main) {
                try {
                    val re: RecyclerView = view.findViewById(R.id.review_recycle)
                    linearlayoutManager= LinearLayoutManager(requireContext())
                    re.layoutManager = linearlayoutManager
                    myAdapter = ItemAdapter(requireContext(), d_data)
                    myAdapter.notifyDataSetChanged()
                    re.adapter = myAdapter
                }
                catch (e:Exception)
                {
                    Log.d("msg890", ""+e)
                }
            }

        }
            }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ReviewsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ReviewsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}