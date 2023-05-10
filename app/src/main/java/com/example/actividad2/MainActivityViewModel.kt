package com.example.actividad2

import androidx.lifecycle.ViewModel

class MainActivityViewModel(private val model: Model) : ViewModel() {

    val animals = model.animals

    fun removeAnimal(animal: Animal) {
        model.removeAnimal(animal)
    }

}