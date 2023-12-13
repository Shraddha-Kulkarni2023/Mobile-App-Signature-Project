package com.example.sigproj

import android.R.*
import android.app.LauncherActivity.ListItem
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.Adapter
import android.widget.ArrayAdapter
import androidx.activity.ComponentActivity
import androidx.compose.material3.ListItem
import com.example.sigproj.databinding.ActivityMainBinding
import com.example.sigproj.databinding.Page3Binding
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import android.widget.ListView


class GuestDetails: ComponentActivity() {


    private lateinit var binding: Page3Binding
    private lateinit var databaseReference: DatabaseReference
    private lateinit var listView: ListView
    private lateinit var adapter:ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = Page3Binding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Add this in your Application class or main activity
        FirebaseApp.initializeApp(this)

        // Initialize Firebase
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
        databaseReference = FirebaseDatabase.getInstance().reference
        // Read data from "users" node
        databaseReference.child("Child").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // This method is called once with the initial value and whenever data at this location is updated.

                val items = mutableListOf<String>()

                for (userSnapshot in snapshot.children) {
                    val childname = userSnapshot.child("childname").value.toString()
                    val parentcontact = userSnapshot.child("parentcontact").value.toString()
                    val parentname = userSnapshot.child("parentname").value.toString()

                    val item = "Child name: $childname\n   Parent name: $parentname\n  Parent Contact: $parentcontact"
                    items.add(item)
                }

                val listView: ListView = findViewById(R.id.guest)

                adapter = ArrayAdapter(this@GuestDetails, android.R.layout.simple_list_item_1, items)
                listView.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                // ...
                print("Failed")
            }
        })

        binding.backbutton.setOnClickListener() {

            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
            finish()
        }


    }

    }
