package com.example.actividad2

import com.example.datastoreapp.StoredMovie

data class Movie(
    val id: Int,
    val name: String,
    val release: String,
    val playtime: String,
    val plot: String,
    val poster: String,
    val gif: String
) {
    companion object {
        fun fromStoredMovie(storedMovie: StoredMovie): Movie {
            return Movie(
                storedMovie.id,
                storedMovie.name,
                storedMovie.release,
                storedMovie.playtime,
                storedMovie.plot,
                storedMovie.poster,
                storedMovie.gif
            )
        }

    }

    fun asStoredAnimal(): StoredMovie {
        return StoredMovie.newBuilder()
            .setId(id)
            .setName(name)
            .setRelease(release)
            .setPlaytime(playtime)
            .setPlot(plot)
            .setPoster(poster)
            .setGif(gif)
            .build()
    }
}