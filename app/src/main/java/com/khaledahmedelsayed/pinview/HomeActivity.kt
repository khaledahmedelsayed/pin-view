package com.khaledahmedelsayed.pinview

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Toast.makeText(this,"Welcome !", Toast.LENGTH_SHORT).show()
    }
}