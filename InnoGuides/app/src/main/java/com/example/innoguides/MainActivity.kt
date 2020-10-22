package com.example.innoguides

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun buttonClick(view: View) {
        Toast.makeText(this, "Loading of the map", Toast.LENGTH_SHORT).show()

        val loc = Intent(this, Location::class.java);
        startActivity(loc);

        val intent = Intent(this, MapsActivity::class.java)
        startActivity(intent)
    }
}