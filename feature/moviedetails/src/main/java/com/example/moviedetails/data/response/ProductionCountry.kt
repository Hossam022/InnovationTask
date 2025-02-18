package com.example.moviedetails.data.response

import com.squareup.moshi.Json

data class ProductionCountry(
    @Json(name = "iso_3166_1")
    val iso: String,
    val name: String
)
