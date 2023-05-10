package com.example.actividad2

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

    private lateinit var animalAdapter: AnimalAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        d { "onCreate" }

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