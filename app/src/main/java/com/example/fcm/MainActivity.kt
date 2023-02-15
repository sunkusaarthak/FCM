package com.example.fcm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.FirebaseApp
import com.google.firebase.iid.FirebaseInstanceId

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FirebaseApp.initializeApp(this)
        val t = getFCMToken()
        if (t != null) {
            findViewById<TextView>(R.id.tv1).text = t
            Toast.makeText(this, t, Toast.LENGTH_SHORT).show()
            Log.d("FCMToken", t)
        }
        val send : Button = findViewById(R.id.button)
        send.setOnClickListener {
            FirebaseNotificationSender(getString(R.string.Key),
                t,
                "Hola!",
                "This is just for Fun Beta!",
                applicationContext,
                this).sendNotifications()
        }
    }

    private fun getFCMToken(): String? {
        return FirebaseInstanceId.getInstance().token
    }
}