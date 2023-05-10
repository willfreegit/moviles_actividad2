package com.example.actividad2

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import com.example.actividad2.ws.ApiAdapter
import com.example.actividad2.ws.ApiService
import com.github.ajalt.timberkt.d
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import retrofit2.create

class MainActivity : AppCompatActivity() {
    private val viewModel: MainActivityViewModel by viewModel()

    private lateinit var animalAdapter: AnimalAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        d { "onCreate" }

        val movies = ApiAdapter.getInstance().create(ApiService::class.java)
        GlobalScope.launch {
            val result = movies.getMovies()
            println("peliculas:")
            for(x in result){
                println(x.name)
            }
        }

        // crate the adapter
        animalAdapter = AnimalAdapter(
            animalSelected = {
                d { "Selected animal $it!!!" }
            },
            removeAnimal = {
                d { "Remove animal $it !!!" }
                removeAnimal(it)
            }
        )

        // set the adapter
        findViewById<RecyclerView>(R.id.animalList).adapter = animalAdapter

        // subscribe to data changes
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED){

                // collect list of Animal's
                viewModel.animals.collect {
                    // submit list
                    animalAdapter.submitList(it)
                }

            }
        }

    }

    private fun removeAnimal(animal: Animal) {
        viewModel.removeAnimal(animal)
    }
}