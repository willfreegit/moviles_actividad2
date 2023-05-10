package com.example.actividad2.ws

import com.example.actividad2.Movie
import retrofit2.Call
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST


interface ApiService {
    @GET("/skydoves/176c209dbce4a53c0ff9589e07255f30/raw/6489d9712702e093c4df71500fb822f0d408ef75/DisneyPosters2.json")
    suspend fun getMovies(): List<Movie>
}