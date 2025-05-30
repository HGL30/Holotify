package com.example.holotify

import android.annotation.SuppressLint
import android.os.Bundle
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.holotify.ui.LoginActivity
import com.example.holotify.fragments.*
import com.example.holotify.ui.RegisterActivity

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var toolbar: Toolbar
    private lateinit var tvWelcome: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawerLayout)
        navigationView = findViewById(R.id.navigationView)
        toolbar = findViewById<Toolbar>(R.id.toolbar)

        setSupportActionBar(toolbar)
        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        val username = intent.getStringExtra("username")
        val token = intent.getStringExtra("token")

        val navView = findViewById<NavigationView>(R.id.navigationView)
        val headerView = navView.getHeaderView(0)
        val tvWelcome = headerView.findViewById<TextView>(R.id.textViewUserName)
        if(!username.isNullOrEmpty()) {
            tvWelcome.text = "Xin chào, $username 👋"
        }
        else {
            tvWelcome.text = "Đăng nhập"
        }

        // Load mặc định Fragment Trang chủ
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, HomeFragment())
            .commit()

        navigationView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> loadFragment(HomeFragment())
                R.id.nav_favorites -> loadFragment(FavoriteFragment())
                R.id.nav_history -> loadFragment(HistoryFragment())
                R.id.nav_playlist -> loadFragment(PlaylistFragment())
                R.id.nav_settings -> loadFragment(SettingFragment())
                R.id.nav_logout -> {
                    Toast.makeText(this, "Đăng xuất", Toast.LENGTH_SHORT).show()
                    val i = Intent(this, LoginActivity::class.java)
                    startActivity(i)
                    finish()
                }
            }
            drawerLayout.closeDrawers()
            true
        }

        tvWelcome.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}
