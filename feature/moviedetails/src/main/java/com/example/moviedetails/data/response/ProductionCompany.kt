package com.example.moviedetails.data.response

import com.squareup.moshi.Json

data class ProductionCompany(
    val id: Int,
    @Json(name = "logo_path")
    val logoPath: String? = null,
    val name: String,
    @Json(name = "origin_country")
    val originCountry: String
)
