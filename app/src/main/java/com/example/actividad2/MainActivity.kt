package com.example.actividad2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import com.github.ajalt.timberkt.d
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val viewModel: MainActivityViewModel by viewModel()

    private lateinit var movieAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        d { "onCreate" }

        // crate the adapter
        movieAdapter = MovieAdapter(
            movieSelected = {
                d { "Selected movie $it!!!" }
            },
            removeMovie = {
                d { "Remove movie $it !!!" }
                removeMovie(it)
            },
            clicMovie = {
                clicMovie(it)
            }
        )

        // set the adapter
        findViewById<RecyclerView>(R.id.movieList).adapter = movieAdapter

        // subscribe to data changes
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED){

                // collect list of Animal's
                viewModel.movies.collect {
                    // submit list
                    movieAdapter.submitList(it)
                }

            }
        }

    }

    private fun removeMovie(movie: Movie) {
        viewModel.removeMovie(movie)
    }

    private fun clicMovie(movie: Movie){
        val intent = Intent(this, Details::class.java)
        intent.putExtra("name", movie.name)
        intent.putExtra("gif", movie.gif)
        intent.putExtra("plot", movie.plot)
        startActivity(intent)
    }
}