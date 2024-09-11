package com.example.yelp.api

data class Reviews(
    val possible_languages: List<String>,
    val reviews: List<Review>,
    val total: Int
)