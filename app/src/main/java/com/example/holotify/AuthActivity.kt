package com.example.holotify

import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import androidx.appcompat.app.AppCompatActivity

class AuthActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
    }

    fun registerUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("Auth", "Đăng ký thành công")
                } else {
                    Log.e("Auth", "Đăng ký thất bại: ${task.exception?.message}")
                }
            }
    }

    fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("Auth", "Đăng nhập thành công")
                } else {
                    Log.e("Auth", "Đăng nhập thất bại: ${task.exception?.message}")
                }
            }
    }
}