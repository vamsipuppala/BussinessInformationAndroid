package com.example.yelp.api

data class ipinfo(
    val city: String,
    val country: String,
    val hostname: String,
    val ip: String,
    val loc: String,
    val org: String,
    val postal: String,
    val region: String,
    val timezone: String
)