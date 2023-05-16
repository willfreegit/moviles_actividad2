package com.example.actividad2

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.ajalt.timberkt.d
import com.squareup.picasso.Picasso

class MovieAdapter(
    val movieSelected: (Movie) -> Unit,
    val removeMovie: (Movie) -> Unit,
    val clicMovie: (Movie) -> Unit
) :
    ListAdapter<Movie, MovieViewHolder>(MovieDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        d { "onCreateViewHolder()" }
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_item_movie, parent, false)
        return MovieViewHolder(
            view,
            { movieSelected(getItem(it)) },
            { removeMovie(getItem(it)) },
            { clicMovie(getItem(it))}
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        d { "onBindViewHolder(): ${getItem(position).name}" }
        holder.bind(getItem(position))
    }

}

class MovieViewHolder(
    view: View,
    movieSelected: (Int) -> Unit,
    removeMovie: (Int) -> Unit,
    clicMovie: (Int) -> Unit
) : RecyclerView.ViewHolder(view) {
    private val nameTextView: TextView = view.findViewById(R.id.movieName)
    private val movieReleaseTextView: TextView = view.findViewById(R.id.movieRelease)
    private val moviePosterImage: ImageView = view.findViewById(R.id.moviePoster)
    private val removeButton: ImageButton = view.findViewById(R.id.removeButton)

    init {
        view.setOnClickListener {
            movieSelected(adapterPosition)
        }
        removeButton.setOnClickListener {
            d { "Remove movie...." }
            removeMovie(adapterPosition)
        }
        moviePosterImage.setOnClickListener {
            clicMovie(adapterPosition)
        }
    }

    fun bind(movie: Movie) {

        nameTextView.text = movie.name
        movieReleaseTextView.text = movie.release

        Picasso.get()
            .load(Uri.parse(movie.poster))
            .resize(200, 200)
            .centerInside()
            .placeholder(R.drawable.camera_image)
            .into(moviePosterImage)
    }
}

private object MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {

    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }

}