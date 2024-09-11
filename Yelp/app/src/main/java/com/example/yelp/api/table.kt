package com.example.yelp.api

data class table(
    val businesses: List<Businesse>,
    val region: Region,
    val total: Int
)