package com.example.holotify

import android.os.Bundle
import com.google.firebase.FirebaseApp
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this) // Khởi tạo Firebase
    }
}