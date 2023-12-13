package com.example.sigproj

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.sigproj.databinding.Page2Binding


class MainActivity2: ComponentActivity() {

    private lateinit var binding: Page2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = Page2Binding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.button5.setOnClickListener() {

            val intent = Intent(this, MainActivity1::class.java)
            startActivity(intent)
            finish()
        }

        binding.button2.setOnClickListener() {

            val intent = Intent(this, GuestDetails::class.java)
            startActivity(intent)
            finish()
        }

        binding.button3.setOnClickListener() {

            val intent = Intent(this, party::class.java)
            startActivity(intent)
            finish()

        }

        binding.button4.setOnClickListener() {

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()

        }

        binding.qrcode.setOnClickListener() {

            val intent = Intent(this, qrcode::class.java)
            startActivity(intent)
            finish()


        }
    }
}








