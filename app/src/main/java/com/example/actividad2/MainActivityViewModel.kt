package com.example.actividad2

import androidx.lifecycle.ViewModel

class MainActivityViewModel(private val model: Model) : ViewModel() {

    val movies = model.movies

    fun removeMovie(movie: Movie) {
        model.removeMovie(movie)
    }

}