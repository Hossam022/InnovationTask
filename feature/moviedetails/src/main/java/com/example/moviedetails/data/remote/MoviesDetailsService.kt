package com.example.moviedetails.data.remote

import com.example.common.utils.Constants
import com.example.moviedetails.data.response.MovieDetail
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesDetailsService {

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = Constants.API_KEY,
        @Query("language") language: String = Constants.LANGUAGE,
    ): MovieDetail
}