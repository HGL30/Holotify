package com.example.holotify.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.holotify.adapters.SongAdapter
import com.example.holotify.databinding.ActivityHomeBinding
import com.example.holotify.models.Song
import com.example.holotify.utils.FirebaseUtils
import com.google.firebase.firestore.FirebaseFirestore

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val songList = mutableListOf<Song>()
    private lateinit var adapter: SongAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = SongAdapter(songList) { song ->
            val intent = Intent(this, PlayerActivity::class.java).apply {
                putExtra("SONG_URL", song.audioUrl)
            }
            startActivity(intent)
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        fetchSongs()
    }

    private fun fetchSongs() {
        FirebaseFirestore.getInstance().collection("songs")
            .get()
            .addOnSuccessListener { result ->
                songList.clear()
                for (document in result) {
                    val song = document.toObject(Song::class.java)
                    songList.add(song)
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
            }
    }
}
