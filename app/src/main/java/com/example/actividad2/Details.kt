package com.example.actividad2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView


class Details : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val result: TextView = findViewById(R.id.result)
        val back: Button = findViewById(R.id.back)
        result.setText(intent.getStringExtra("result"))
        back.setOnClickListener {
            back()
        }
    }

    fun back() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}