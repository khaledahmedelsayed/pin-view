package com.khaledahmedelsayed.pinview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class PinCodeTestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pinView.setOnCompletedListener = { pinCode ->

            if(pinCode == "1234")
                startActivity(Intent(this,HomeActivity::class.java))

            else
                pinView.showError(true)

            pinView.clearPin()
        }

        pinView.setOnPinKeyClickListener = { keyPressed ->
            Toast.makeText(this,"Key pressed was $keyPressed",Toast.LENGTH_SHORT).show()
        }

    }
}