package com.example.actividad2

import com.example.datastoreapp.StoredAnimal
import com.squareup.moshi.Json

data class Animal(
    val id: Int,
    val name: String,
    @Json(name = "latin_name")
    val latinName: String,
    @Json(name = "geo_range")
    val geoRange: String,
    val diet: String,
    @Json(name = "image_link")
    val imageUrl: String
) {
    companion object {
        fun fromStoredAnimal(storedAnimal: StoredAnimal): Animal {
            return Animal(
                storedAnimal.id,
                storedAnimal.name,
                storedAnimal.latinName,
                storedAnimal.geoRange,
                storedAnimal.diet,
                storedAnimal.imageUrl
            )
        }

    }

    fun asStoredAnimal(): StoredAnimal {
        return StoredAnimal.newBuilder()
            .setId(id)
            .setName(name)
            .setLatinName(latinName)
            .setGeoRange(geoRange)
            .setImageUrl(imageUrl)
            .build()
    }
}