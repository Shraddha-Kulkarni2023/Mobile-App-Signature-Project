
@file:Suppress("DEPRECATION")
package com.example.sigproj

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.telephony.SmsManager
import androidx.activity.ComponentActivity
import com.google.firebase.database.FirebaseDatabase
import android.app.PendingIntent
import android.content.pm.PackageManager
import android.telephony.SmsMessage
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.sigproj.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.*
import android.content.Context

class MainActivity1 : ComponentActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var pendingIntent: PendingIntent


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        binding.button.setOnClickListener() {

            var database = FirebaseDatabase.getInstance().getReference("Child")

            var childno = binding.txt6.text.toString().toInt()
            var childname = binding.txt1.text.toString()
            var parentname = binding.txt2.text.toString()
            var parentcontact = binding.txt3.text.toString().toInt()
            var datetime = "20th Dec 5.30pm"
            var venue = "Sky Zone Fremont"
            database.child(childno.toString())
                .setValue(Child(childname, parentcontact, parentname))

            var data1 = "Shubhaam's Birthday Invite" + "\n" + datetime + "\n" + venue

            if (ActivityCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.RECEIVE_SMS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.RECEIVE_SMS, Manifest.permission.SEND_SMS),
                    111
                )
            } else
                sendSms(parentcontact.toString(), data1)

        }



        binding.back.setOnClickListener() {

            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
            finish()

        }

    }

    private fun signOutAndStartSignInActivity() {


    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 111 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            sendSms("123", "Hi")
    }

    private fun sendSms(phoneNumber: String, message: String) {
        val smsManager = SmsManager.getDefault()
        val sentIntent = PendingIntent.getBroadcast(
            this, 0, Intent("SMS_SENT"),
            PendingIntent.FLAG_IMMUTABLE
        )
        val deliveredIntent = PendingIntent.getBroadcast(
            this, 0, Intent("SMS_DELIVERED"),
            PendingIntent.FLAG_IMMUTABLE
        )

        val parts = smsManager.divideMessage(message)
        val sendList = ArrayList<PendingIntent>()
        val deliverList = ArrayList<PendingIntent>()

        for (i in parts.indices) {
            sendList.add(sentIntent)
            deliverList.add(deliveredIntent)
        }

        // Send the SMS
        smsManager.sendMultipartTextMessage(phoneNumber, null, parts, sendList, deliverList)
    }

}




