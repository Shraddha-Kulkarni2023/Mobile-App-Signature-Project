package com.example.sigproj

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.sigproj.databinding.ActivityMainBinding
import com.example.sigproj.databinding.PartybannerBinding

class party: ComponentActivity() {

    private lateinit var binding: PartybannerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = PartybannerBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.button6.setOnClickListener() {

            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
            finish()
        }


    }


}