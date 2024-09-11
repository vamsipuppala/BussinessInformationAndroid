package com.example.yelp

import com.example.yelp.api.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiRequests {
    @GET("/")
    fun getIpInfo(@Query("token") token:String="d3c6a17410f175"): Call <ipinfo>
    @GET("/geo/")
    fun getLocInfo(@Query("loc") loc:String): Call<temp>
    @GET("/table/")
    fun getTableInfo(@Query("term") term:String, @Query("cat") cat:String, @Query("rad") rad:Int, @Query("lat") lat:Double, @Query("long") long:Double) : Call<table>
    @GET("/business/")
    fun getDetailInfo(@Query("id") id:String): Call<Details>
    @GET("/business/")
    fun getReviewInfo(@Query("id") id:String): Call<Reviews>

    @GET("/auto/")
    fun getAutoResults(
        @Query("typed")
        value : String = "Pizza"
    ): Call <List<String>>
}