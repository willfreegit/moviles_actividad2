package com.example.actividad2

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.ajalt.timberkt.d
import com.squareup.picasso.Picasso

class AnimalAdapter(
    val animalSelected: (Animal) -> Unit,
    val removeAnimal: (Animal) -> Unit,
) :
    ListAdapter<Animal, AnimalViewHolder>(AnimalDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder {
        d { "onCreateViewHolder()" }
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_item_animal, parent, false)
        return AnimalViewHolder(
            view,
            { animalSelected(getItem(it)) },
            { removeAnimal(getItem(it)) }
        )
    }

    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {
        d { "onBindViewHolder(): ${getItem(position).name}" }
        holder.bind(getItem(position))
    }

}

class AnimalViewHolder(
    view: View,
    animalSelected: (Int) -> Unit,
    removeAnimal: (Int) -> Unit
) : RecyclerView.ViewHolder(view) {
    private val nameTextView: TextView = view.findViewById(R.id.animalName)
    private val geoRangeTextView: TextView = view.findViewById(R.id.geoRange)
    private val animalImage: ImageView = view.findViewById(R.id.animalImage)
    private val removeButton: ImageButton = view.findViewById(R.id.removeButton)

    init {
        view.setOnClickListener {
            animalSelected(adapterPosition)
        }
        removeButton.setOnClickListener {
            d { "Remove animal...." }
            removeAnimal(adapterPosition)
        }
    }

    fun bind(animal: Animal) {

        nameTextView.text = animal.name
        geoRangeTextView.text = animal.geoRange

        Picasso.get()
            .load(Uri.parse(animal.imageUrl))
            .resize(200, 200)
            .centerInside()
            .placeholder(R.drawable.camera_image)
            .into(animalImage)
    }
}

private object AnimalDiffCallback : DiffUtil.ItemCallback<Animal>() {

    override fun areItemsTheSame(oldItem: Animal, newItem: Animal): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Animal, newItem: Animal): Boolean {
        return oldItem == newItem
    }

}