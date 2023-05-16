package com.example.actividad2

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso


class Details : AppCompatActivity() {
    private lateinit var movies: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val result: TextView = findViewById(R.id.result)
        val back: Button = findViewById(R.id.back)
        val movieGif: ImageView = findViewById(R.id.movieGif)
        val name = intent.getStringExtra("name");
        val gif = intent.getStringExtra("gif");

        //val movie = movies.movies.value.get(position);

        result.setText(name);
        Picasso.get()
            .load(Uri.parse(gif))
            .resize(200, 200)
            .centerInside()
            .placeholder(R.drawable.camera_image)
            .into(movieGif)

        back.setOnClickListener {
            back()
        }
    }

    fun back() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}