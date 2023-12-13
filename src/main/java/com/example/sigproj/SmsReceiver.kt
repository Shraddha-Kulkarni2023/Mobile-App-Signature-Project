package com.example.sigproj

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.telephony.SmsMessage
import android.util.Log
import android.os.Bundle
import android.telephony.SmsManager


public class SmsReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION == intent?.action) {
            val bundle: Bundle? = intent.extras
            if (bundle != null) {
                val pdus: Array<Any>? = bundle.get("pdus") as Array<Any>?
                if (pdus != null) {
                    for (pdu in pdus) {
                        val smsMessage: SmsMessage = SmsMessage.createFromPdu(pdu as ByteArray)
                        val senderPhoneNumber: String? = smsMessage.originatingAddress
                        val messageBody: String? = smsMessage.messageBody

                        // Check for null before using the values
                        if (senderPhoneNumber != null && messageBody != null) {
                            Log.d("SMSReceiver", "Received SMS from $senderPhoneNumber with body: $messageBody")
                            val acknowledgmentMessage = "Please sign waiver prior to the party"
                            sendSms(senderPhoneNumber, acknowledgmentMessage)

                        }
                    }
                }
            }
        }
    }

    private fun sendSms(phoneNumber: String, message: String) {
        try {
            val smsManager = SmsManager.getDefault()
            smsManager.sendTextMessage(phoneNumber, null, message, null, null)
            Log.d("SmsReceiver", "Acknowledgment SMS sent to $phoneNumber: $message")
        } catch (e: Exception) {
            Log.e("SmsReceiver", "Error sending SMS: $e")
        }
    }
}
