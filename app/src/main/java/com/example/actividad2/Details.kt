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

        val movieName: TextView = findViewById(R.id.name)
        val moviePlot: TextView = findViewById(R.id.plot)
        val back: Button = findViewById(R.id.back)
        val movieGif: ImageView = findViewById(R.id.movieGif)
        val name = intent.getStringExtra("name");
        val plot = intent.getStringExtra("plot");
        val gif = intent.getStringExtra("gif");

        movieName.setText(name);
        moviePlot.setText(plot);
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