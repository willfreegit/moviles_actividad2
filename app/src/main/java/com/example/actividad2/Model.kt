package com.example.actividad2

import androidx.datastore.core.DataStore
import com.example.actividad2.ws.ApiAdapter
import com.example.actividad2.ws.ApiService
import com.example.datastoreapp.MovieStore
import com.github.ajalt.timberkt.d
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class Model(private val moviesDataStore: DataStore<MovieStore>) {

    private val coroutineScope = CoroutineScope(Job() + Dispatchers.Default)

    private val _movies = MutableStateFlow<List<Movie>>(listOf())
    val movies = _movies as StateFlow<List<Movie>>

    init {
        coroutineScope.launch {
            //TODO 10 use the moviesDataSTore as will (with coroutines)
            moviesDataStore.data
                .map { it.initialized }
                .filter { !it }
                .first {
                    d { "Initialize data store..." }
                    initDataStore()
                    return@first true
                }
        }

        coroutineScope.launch {
            moviesDataStore.data
                .collect { movieStore ->
                    d { "Movies count: ${movieStore.moviesCount}" }
                    val movies = movieStore.moviesList.map { Movie.fromStoredMovie(it) }
                    _movies.emit(movies)
                }
        }

    }

    private fun initDataStore() {
        // create moshi parser
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val type = Types.newParameterizedType(List::class.java, Movie::class.java)
        val adapter = moshi.adapter<List<Movie>>(type)

        // create the storedAnimals list
        val movies = ApiAdapter.getInstance().create(ApiService::class.java)
        GlobalScope.launch {
            val moviesFromJson: List<Movie>  = movies.getMovies()
            val moviesToStore = moviesFromJson.map { it.asStoredMovie() }
            // save data to data store
            coroutineScope.launch {
                moviesDataStore.updateData { movieStore ->
                    movieStore.toBuilder()
                        .addAllMovies(moviesToStore)
                        .setInitialized(true)
                        .build()
                }
            }
        }
    }

    fun removeMovie(movie: Movie) {
        val toStoreMovies = movies.value
            .filter { it.id != movie.id }
            .map { it.asStoredMovie() }

        coroutineScope.launch {
            moviesDataStore.updateData { movieStore ->
                movieStore.toBuilder()
                    .clearMovies()
                    .addAllMovies(toStoreMovies)
                    .build()
            }
        }
    }

}